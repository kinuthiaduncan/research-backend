<template>
    <div class="">
        <h4>Here</h4>
    </div>
</template>

<script>
    import { mapActions } from 'vuex';
    export default {
        data: function () {
            return {
                pageName: "VPN Usage Analysis"
            }
        },
        methods: {
            ...mapActions([
                'listenToPageChange'
            ])
        },
        mounted: function () {
            this.listenToPageChange(this.pageName);
        }
    }
</script>

<style scoped>

</style>