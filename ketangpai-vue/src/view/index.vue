<template>
    <div class="wrap">
        <el-container>
            <el-header style="height:60px">
                <img src="../assets/logo.png" />
            </el-header>
            <el-main>
                <el-carousel height="520px" arrow="never">
                    <el-carousel-item>
                        <img src="../assets/banner1.png" style="width:100%;height:100%" />
                    </el-carousel-item>
                    <el-carousel-item>
                        <img src="../assets/banner2.png" style="width:100%;height:100%" />
                    </el-carousel-item>
                </el-carousel>
            </el-main>
            <el-footer style="height:212px">
                <el-row>
                    <el-col :span="12">
                        <el-button
                            class="button-log"
                            @click="jumpToLoginPage()"
                            @mouseover="hover()"
                        >登录</el-button>
                    </el-col>
                    <el-col :span="12" >
                        <el-button
                            type="primary"
                            class="button-reg"
                            @click="jumpToRegisterPage()"
                        >注册</el-button>
                    </el-col>
                </el-row>
            </el-footer>
        </el-container>
    </div>
</template>
<script>
export default {
    data(){
        return{
            isLogin:false,
        }
    },
    methods: {
        jumpToLoginPage() {
            this.$router.push({ name: "login" });
        },
        jumpToRegisterPage() {
            this.$router.push({ name: "register" });
        },
    },
    mounted(){
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
    overflow-y: auto;
    font-family: 微软雅黑;
}
.el-header {
    padding: 0px;
    background-color: rgb(39, 46, 56);
    padding-left: 50px;
    padding-top: 14px;
}
.el-main {
    padding: 0px;
}
.el-footer {
    padding: 0px;
    background-color: rgb(39, 46, 56);
}
.el-col {
    text-align: center;
    padding-top: 56px;
}
.button-log {
    background-color: rgb(39, 46, 56);
    color: white;
    height: 100px;
    width: 230px;
    font-size: 30px;
}
.button-log:hover {
    color: rgb(64, 158, 255);
    border-color: rgb(64, 158, 255);
}
.button-reg {
    color: white;
    height: 100px;
    width: 230px;
    font-size: 30px;
}
</style>