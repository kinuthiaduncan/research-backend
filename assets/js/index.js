require('./config');
import VueRouter from 'vue-router';
Vue.use(VueRouter);

import store from './stores/store';

import vpn from './components/FocusGroups/Vpn.vue';
import dashboard from './components/Dashboard.vue';

const routes = [
    {path: '/', component: dashboard, name: 'dashboard', alias: '/dashboard'},
    {path: '/vpn', component: vpn, name: 'vpn'},
];

const router = new VueRouter({ routes });

const app = new Vue ({
    router,
    store,
    data: function () {
        return {

        }
    }
}).$mount('#app');