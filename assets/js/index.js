require('./config');

Vue.component('dashboard', require('./components/Dashboard.vue').default);
const app = new Vue ({
    data: function () {
        return {

        }
    }
}).$mount('#app');