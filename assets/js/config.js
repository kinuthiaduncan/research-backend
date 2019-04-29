window.$ = window.jQuery = require('jquery');
require('bootstrap-sass');
window.Vue = require('vue');

window.axios = require('axios');

window.axios.defaults.headers.common = {
    'Csrf-Token': window.Play.csrfToken,
    'X-Requested-With': 'XMLHttpRequest'
};

import 'es6-promise/auto';