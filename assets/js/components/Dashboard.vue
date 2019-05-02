<template>
    <div class="page-container">
        <div class="sidebar-menu">
            <div class="sidebar-header">
                <div class="logo">
                    <!--<a href="index.html"><img src="assets/images/icon/logo.png" alt="logo"></a>-->
                </div>
            </div>
            <div class="main-menu">
                <div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 517px;">
                    <div class="menu-inner" style="overflow: hidden; width: auto; height: 517px;">
                        <el-menu
                        default-active="2"
                        class="el-menu-vertical-demo"
                        :collapse="isCollapse"
                        background-color="#303641">
                        <el-submenu index="1">
                            <template slot="title">
                                <span class="iconify" data-icon="simple-line-icons:people" data-inline="false"></span>
                                <span slot="title">Focus Groups</span>
                            </template>
                            <el-menu-item index="1-1">item one</el-menu-item>
                        </el-submenu>
                        </el-menu>
                    </div>
                </div>
            </div>
        </div>
        <div class="main-content" style="min-height: 274px;">
            <div class="header-area">
                <div class="row align-items-center">
                    <div class="col-md-6 col-sm-8 clearfix">
                        <div class="nav-btn pull-left">
                            <span class="iconify" data-icon="simple-line-icons:menu" data-inline="false"></span>
                        </div>
                        <div class="search-box pull-left">
                            <!--<form action="#">-->
                                <!--<input type="text" name="search" placeholder="Search..." required="">-->
                                <!--<i class="ti-search"></i>-->
                            <!--</form>-->
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-4 clearfix">

                    </div>
                </div>
            </div>

            <div class="page-title-area">
                <div class="row align-items-center">
                    <div class="col-sm-6">
                        <div class="breadcrumbs-area clearfix">
                            <h4 class="page-title pull-left">Dashboard</h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="main-content-inner">

            </div>

        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                isCollapse: false,
                activeIndex: '1',
                activeIndex2: '1'
            };
        },
        methods: {
            menuControl: function () {
                this.isCollapse = !this.isCollapse;
            }
        }
    }
</script>
<style>
</style>