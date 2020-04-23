<template>
    <div class="wrap">
        <div class="menu">
            <el-menu :default-active="activeIndex" class="el-menu" mode="horizontal">
                <img src="../assets/logo-mainblue.png" class="logo" />
                <el-menu-item index="1" @click.native="changeActive('1')">
                    <router-link to="/course" style="padding=20px">课堂</router-link>
                </el-menu-item>
                <el-dropdown trigger="click" class="message" placement="bottom">
                    <el-badge :value="9" class="item">
                        <span class="iconfont icon-icon-xiaoxi"></span>
                    </el-badge>
                    <el-dropdown-menu slot="dropdown" class="message-s">
                        <el-dropdown-item icon="el-icon-setting">个人设置</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-circle-close">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <el-dropdown trigger="click" class="avatar" placement="bottom-end">
                    <el-avatar size="small" src style="margin-top:22px; cursor: pointer;"></el-avatar>
                    <el-dropdown-menu slot="dropdown" class="avatar-s">
                        <router-link to="/userInfo">
                            <el-dropdown-item
                                icon="iconfont icon-shezhi"
                                @click.native="changeActive('')"
                            >个人设置</el-dropdown-item>
                        </router-link>
                        <el-dropdown-item icon="iconfont icon-tuichudenglu1" @click.native="exit">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-menu>
        </div>
        <router-view></router-view>
    </div>
</template>
<script>
export default {
    data() {
        return {
            activeIndex: "1"
        };
    },
    methods: {
        changeActive(key) {
            this.activeIndex = key;
        },
        exit(){
            localStorage.clear();
             this.$router.push({name:"index"});
        }
    },
    mounted() {},
    created() {
        if (this.$route.path == "/course") {
            this.activeIndex = "1";
        }
        if (this.$route.path == "/userInfo") {
            this.activeIndex = "";
        }
    }
};
</script>
<style scoped>
.wrap {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    font-family: "PingFang SC", tahoma, arial, "helvetica neue",
        "hiragino sans gb", "microsoft yahei ui", "microsoft yahei", simsun,
        sans-serif;
}
.menu {
    position: fixed;
    height: 72px;
    width: 100%;
    border-bottom: solid 1px #e6e6e6;
    box-shadow: 0 0 10px #ccc;
    z-index: 999;
}
.el-menu {
    width: 100%;
    height: 100%;
    line-height: 73px;
}
.el-menu >>> .el-menu-item {
    height: 100%;
    line-height: 73px;
    padding: 0 !important;
}
a {
    text-decoration: none;
}
.el-menu >>> .el-menu-item a {
    padding: 20px;
    text-decoration: none;
}
.logo {
    margin-top: 24px;
    margin-left: 70px;
    margin-right: 40px;
    float: left;
    width: 96px;
    height: 24px;
    cursor: pointer;
}
.message-s {
    top: 50px !important;
}
.avatar-s {
    top: 50px !important;
}
.el-dropdown-menu__item:focus,
.el-dropdown-menu__item:not(.is-disabled):hover {
    background-color: transparent;
    color: #66b1ff;
}
.item >>> .el-badge__content.is-fixed {
    top: 20px;
    right: 14px;
}
.item {
    display: block !important;
}
.message {
    height: 72px;
    top: -3px;
    margin-left: 1080px;
}
.avatar {
    height: 72px;
    margin-left: 50px;
}
.iconfont.icon-icon-xiaoxi {
    margin-top: 22px;
    cursor: pointer;
    font-size: 28px;
}
</style>