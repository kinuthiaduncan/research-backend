<template>
    <div class="dashboard-main">
        <div class="row">
            <div class="col-md-4">
                <div class="single-report mb-xs-30">
                    <h4 class="header-title">All Focus Groups Participants</h4>
                    <h2>{{participantCount}}</h2>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import { mapActions } from 'vuex';
    export default {
        data: function() {
            return {
                pageName: "Dashboard",
                all_participants: "/focus_groups/all_participants",
                participantCount: null
            };
        },
        methods: {
            ...mapActions([
                'listenToPageChange'
            ]),
            getAllParticipants: function () {
                axios.get(this.all_participants).then(response => {
                    this.participantCount = response.data.participantCount;
                }).catch(error => {
                    console.log(error);
                })
            }
        },
        mounted: function () {
            this.listenToPageChange(this.pageName);
            this.getAllParticipants();
        }
    }
</script>
<style>
</style>