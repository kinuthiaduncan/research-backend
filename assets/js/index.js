require('./config');
import VueRouter from 'vue-router';
Vue.use(VueRouter);

import store from './stores/store';
import { mapActions } from 'vuex';

import vpn from './components/FocusGroups/Vpn.vue';
import dashboard from './components/Dashboard.vue';

const routes = [
    {path: '/', component: dashboard, name: '/', alias: '/dashboard'},
    {path: '/vpn', component: vpn, name: 'vpn'},
];

const router = new VueRouter({ routes });

const app = new Vue ({
    router,
    store,
    data: function () {
        return {

        }
    },
    computed: {
        active_page: function () {
            return this.$store.getters.activePage;
        }
    },
    methods: {
        ...mapActions([

        ]),
    }
}).$mount('#app');