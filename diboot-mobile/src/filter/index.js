const moment = require('moment');

export function html2Text(val) {
  const div = document.createElement('div');
  div.innerHTML = val;
  return div.textContent || div.innerText;
}

export function formatNull(val) {
  if (val === '' || val === undefined) {
    return '-';
  } else if (val === true) {
    return '是';
  } else if (val === false) {
    return '否';
  }
  return val;
}

export function date2string(val, format) {
  format = format === undefined ? 'YYYY-MM-DD' : format;
  return moment(val).format(format);
}

export function datetime2string(val, format) {
  format = format === undefined ? 'YYYY-MM-DD h:mm' : format;
  return moment(val).format(format);
}
