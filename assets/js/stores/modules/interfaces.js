const state = {
    active_page: ""
};

const mutations = {
    'UPDATE_PAGE' (state, active_page) {
        state.active_page = active_page;
    }
};

const getters = {
    activePage: (state) => {
        return state.active_page;
    }
};

export default {
    state,
    mutations,
    getters
}