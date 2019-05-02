import Vue from 'vue';
import Vuex from 'vuex';

import actions from './actions';
import interfaces from './modules/interfaces';

Vue.use(Vuex);

export default new Vuex.Store({
   actions,
    modules: {
       interfaces
    }
})