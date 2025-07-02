// static/js/grid/autoCompleteEditor.js

function createAutoCompleteEditor({ urlBuilder, deps = [], onSelectClear = [] }) {
  function CustomEditor(props) {
    const el = document.createElement('input');
    el.type = 'text';
    el.className = 'form-control';
    el.value = props.value || '';

    $(el).autocomplete({
      source: function (request, response) {
        const depValues = deps.map(dep => props.grid.getValue(props.rowKey, dep));
        if (depValues.includes('') || depValues.includes(undefined)) {
          return response([]);
        }

        const url = urlBuilder(...depValues);
        $.ajax({
          url: url,
          method: 'GET',
          dataType: 'json',
          success: function (data) {
            const results = data.filter(v => v.startsWith(request.term));
            response(results);
          },
          error: function (xhr, status, err) {
            console.error('자동완성 실패:', err);
            response([]);
          }
        });
      },
      minLength: 0,
      select: function () {
        onSelectClear.forEach(col => props.grid.setValue(props.rowKey, col, ''));
      }
    }).focus(function () {
      $(this).autocomplete("search", "");
    });

    this.el = el;
  }

  CustomEditor.prototype.getElement = function () {
    return this.el;
  };

  CustomEditor.prototype.getValue = function () {
    return this.el.value;
  };

  return CustomEditor;
}