<template>
    <div class="wrap">
        <el-dialog :visible="true" :show-close="false" width="587.5px" style="height:100%;">
            <span slot="title" class="dialog-header">账号登录</span>
            <el-form ref="user" :model="user">
                <el-form-item>
                    <el-input v-model="user.username" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="user.password" placeholder="密码" type="password"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button
                        type="primary"
                        style="width:527px;height:50px;font-size:16px"
                        @click="login()"
                    >登录</el-button>
                </el-form-item>
            </el-form>
            <div class="foot">
                <router-link to="/register">
                    <span style="color:#AFB1B3">还没有账号？</span>
                    <span style="color:rgb(64, 158, 255)">去注册</span>
                </router-link>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import qs from 'qs'
export default {
    data() {
        return {
            user: {}
        };
    },
    created() {
        document.title = "登录-课堂派";
    },
    methods: {
        /**
                     * 获取localStorage数据
                     * @param {*} key localStorage中的key string
                     * @returns localStorage数据 arr
                     */
                    getDate(key) {
                        var str = localStorage.getItem(key) || "[]"
                        var arr = JSON.parse(str)
                        return arr
                    },
        login() {
            this.$axios
                .post("api/login", qs.stringify(this.user), {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                })
                .then(res => {
                    alert("登录成功");
                    localStorage.setItem('id',JSON.stringify(res.data.msg.id));
                    localStorage.setItem('role',JSON.stringify(res.data.msg.roles[0].id))
                    this.$router.push({name:"headerNav"});
                })
                .catch(err => {
                    alert("账号或密码错误");
                    console.log(err);
                });
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
    overflow-y: auto;
    background: url("../assets/login-bg2.jpg");
    font-family: 微软雅黑;
    background-size: cover;
}
.dialog-header {
    font-size: 26px;
}
.foot {
    margin-top: 20px;
    text-align: right;
}
a {
    text-decoration: none;
}
.el-input >>> .el-input__inner {
    height: 62px;
    font-size: 16px;
}
.el-dialog__wrapper >>> .el-dialog__header {
    height: 93px;
    line-height: 93px;
    padding: 0;
    text-align: center;
}
.el-dialog__wrapper >>> .el-dialog__body {
    padding: 0px 30px;
}
.el-dialog__wrapper >>> .el-dialog {
    height: 380px;
}
</style>