window.$ = window.jQuery = require('jquery');
require('bootstrap-sass');
window.Vue = require('vue');

window.axios = require('axios');

// window.axios.defaults.headers.common = {
//     'Csrf-Token': window.Play.csrfToken,
//     'X-Requested-With': 'XMLHttpRequest'
// };

import ElementUI from 'element-ui';
import locale from 'element-ui/lib/locale/lang/en'
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI, { locale });

import 'es6-promise/auto';

