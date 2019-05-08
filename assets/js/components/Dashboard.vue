<template>
    <div class="dashboard-main">
        <div class="row" style="display:flex;">
            <div class="col-md-4">
                <div class="single-report">
                    <h4 class="header-title">All Focus Groups Participants</h4>
                    <h2>{{participantCount}}</h2>
                </div>
            </div>
            <div class="col-md-4">
                <div class="single-report">
                    <h4 class="header-title">Age Groups Counts</h4>
                    <div v-for="ageGroup in ageGroups">
                        <h2>{{ageGroup.join(' = ')}}</h2>
                    </div>

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
                all_participants: "/dashboard/all_participants",
                participantCount: null,
                age_group_url: "/dashboard/group_by_age",
                ageGroups: [],
                gender_age_group_url: "/focus_groups/gender_age_group"
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
            },
            getAgeGroups: function () {
                axios.get(this.age_group_url).then(response => {
                    this.ageGroups = response.data.ageGroups;

                }).catch(error => {
                    console.log(error);
                })
            },
            genderAgeGroups: function () {
                axios.get(this.gender_age_group_url).then(response => {
                    console.log(response.data);
                }).catch(error => {
                    console.log(error);
                })
            }
        },
        mounted: function () {
            this.listenToPageChange(this.pageName);
            this.getAllParticipants();
            this.getAgeGroups();
            this.genderAgeGroups();
        }
    }
</script>
<style>
</style>