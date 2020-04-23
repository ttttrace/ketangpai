<template>
    <div class="wrap">
        <div class="chooseRole" v-if="chooseRoleDisplay">
            <el-form>
                <h3>注册账号</h3>
                <p>请选择您在日常教学中的实际身份</p>
                <el-row>
                    <img src="../assets/teacher.png" />
                    <el-button type="primary" @click="teacherRegisterPage()">我是老师/助教</el-button>
                </el-row>
                <el-row>
                    <img src="../assets/student.png" />
                    <el-button type="primary" @click="studentRegisterPage()">我是学生</el-button>
                </el-row>
                <div class="foot">
                    <router-link to="/login">
                        <span style="color:#AFB1B3">已有账号?</span>
                        <span style="color:rgb(64, 158, 255)">去登录</span>
                    </router-link>
                </div>
            </el-form>
        </div>
        <div class="teacherRegister" v-if="teacherRegisterDisplay">
            <div class="title-fix">
                <a class="return-fl" @click="backToReg()"></a>
                <h3 class="fl">老师/助教注册</h3>
            </div>
            <el-form :model="userForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
                <el-form-item prop="username">
                    <el-input v-model="userForm.username" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" v-model="userForm.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item prop="name">
                    <el-input v-model="userForm.name" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item prop="school">
                    <el-input v-model="userForm.school" placeholder="学校"></el-input>
                </el-form-item>
                <el-form-item prop="identyInput">
                    <el-input
                        v-model="userForm.identyInput"
                        placeholder="验证码"
                        style="width:220px;float:left"
                    ></el-input>
                    <div class="code" @click="refreshCode">
                        <s-identify :identifyCode="identifyCode"></s-identify>
                    </div>
                </el-form-item>
                <el-button
                    type="primary"
                    style="height:48px;width:100%;margin-left:0"
                    @click="register"
                >注册为老师/助教</el-button>
            </el-form>

            <div class="foot" style="margin-right:40px">
                <router-link to="/login">
                    <span style="color:#AFB1B3">已有账号?</span>
                    <span style="color:rgb(64, 158, 255)">去登录</span>
                </router-link>
            </div>
        </div>
        <div class="studentRegister" v-if="studentRegisterDisplay">
            <div class="title-fix">
                <a class="return-fl" @click="backToReg()"></a>
                <h3 class="fl">学生注册</h3>
            </div>
            <el-form :model="userForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
                <el-form-item prop="username">
                    <el-input v-model="userForm.username" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" v-model="userForm.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item prop="name">
                    <el-input v-model="userForm.name" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item prop="tsid">
                    <el-input v-model="userForm.tsid" placeholder="学号"></el-input>
                </el-form-item>
                <el-form-item prop="school">
                    <el-input v-model="userForm.school" placeholder="学校"></el-input>
                </el-form-item>
                <el-form-item prop="identyInput">
                    <el-input
                        v-model="userForm.identyInput"
                        placeholder="验证码"
                        style="width:220px;float:left"
                    ></el-input>
                    <div class="code" @click="refreshCode">
                        <s-identify :identifyCode="identifyCode"></s-identify>
                    </div>
                </el-form-item>
                <el-button
                    type="primary"
                    style="height:48px;width:100%;margin-left:0"
                    @click="register"
                >注册为学生</el-button>
            </el-form>

            <div class="foot" style="margin-right:40px">
                <router-link to="/login">
                    <span style="color:#AFB1B3">已有账号?</span>
                    <span style="color:rgb(64, 158, 255)">去登录</span>
                </router-link>
            </div>
        </div>
    </div>
    
</template>
<script>
import SIdentify from "@/components/identify.vue";
import qs from "qs";
export default {
    data() {
        var newReg1 = (rule, value, callback) => {
            if (value != this.identifyCode) {
                callback(new Error("验证码错误"));
            } else {
                callback(); //必须要有回调，要不然表单无法提交
            }
        };
        return {
            chooseRoleDisplay: true,
            teacherRegisterDisplay: false,
            studentRegisterDisplay: false,
            rid: "",
            identifyCodes: "1234567890",
            identifyCode: "",
            userForm: {username:"",password:"",tsid:"",name:"",school:""},
            ruleForm: {},
            rules: {
                username: [
                    { required: true, message: "邮箱不能为空", trigger: "blur" },
                    {
                        pattern:/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,
                        message:"邮箱输入有误",
                        trigger:"blur"
                    }
                ],
                name: [
                    {
                        required: true,
                        message: "姓名不能为空",
                        trigger: "blur"
                    },
                    {
                        pattern: /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/,
                        message: "姓名需为中文姓名",
                        trigger: "change"
                    }
                ],
                password: [
                    { required: true, message: "密码不能为空", trigger: "blur" },
                    {
                        pattern:/^(?![A-Za-z]+$)(?![A-Z0-9]+$)(?![a-z0-9]+$)(?![a-z\W_!@#$%^&*`~()-+=]+$)(?![A-Z\W_!@#$%^&*`~()-+=+$)(?![0-9\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\W_!@#$%^&*`~()-+=]{8,16}$/,
                        message:"密码需8-16位(包含大小写字母、特殊符号、数字中的任意三项)",
                        trigger:"blur"
                    }
                ],
                school: [
                    { required: true, message: "学校不能为空", trigger: "blur" }
                ],
                identyInput: [
                    {
                        required: true,
                        message: "验证码不能为空",
                        trigger: "blur"
                    },
                    { validator: newReg1, trigger: "blur" }
                ]
            }
        };
    },
    created() {
        document.title = "账号注册-课堂派";
    },
    components: {
        SIdentify
    },
    methods: {
        backToReg() {
            this.studentRegisterDisplay = false;
            this.teacherRegisterDisplay = false;
            this.userForm = {};
            this.rid = "";
            this.chooseRoleDisplay = true;
        },
        teacherRegisterPage() {
            this.chooseRoleDisplay = false;
            this.teacherRegisterDisplay = true;
            this.rid = "1";
        },
        studentRegisterPage() {
            this.chooseRoleDisplay = false;
            this.studentRegisterDisplay = true;
            this.rid = "2";
        },
        randomNum(min, max) {
            return Math.floor(Math.random() * (max - min) + min);
        },
        refreshCode() {
            this.identifyCode = "";
            this.makeCode(this.identifyCodes, 4);
        },
        makeCode(o, l) {
            for (let i = 0; i < l; i++) {
                this.identifyCode += this.identifyCodes[
                    this.randomNum(0, this.identifyCodes.length)
                ];
            }
            console.log(this.identifyCode);
        },
        register() {
            var user={username:this.userForm.username,password:this.userForm.password};
            var ts={name:this.userForm.name,tsid:this.userForm.tsid,school:this.userForm.school};
            var map={user:user,ts:ts}
            console.log(map)
            this.$axios
                .post("api/user/register/user", map)
                .then(res => {
                    alert("注册成功");
                    this.$axios.post(
                        "api/user/register/role?uid=" + res.data + "&rid=" + this.rid
                    );
                    this.$router.push("login");
                })
                .catch(err => {
                    alert(err.response.data.msg);
                    console.log(err);
                });
        }
    },
    mounted() {
        this.identifyCode = "";
        this.makeCode(this.identifyCodes, 4);
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
    background: url("../assets/reg-bg.png");
    font-family: 微软雅黑;
    background-size: cover;
}
.chooseRole {
    padding: 29.35px;
    margin: auto;
    margin-top: 110px;
    width: 421px;
    height: 455px;
    background: #fff;
    border-radius: 4px;
}
.teacherRegister {
    margin: auto;
    margin-top: 110px;
    width: 480px;
    height: 570px;
    background: #fff;
    border-radius: 4px;
}
.studentRegister {
    margin: auto;
    margin-top: 110px;
    width: 480px;
    height: 615px;
    background: #fff;
    border-radius: 4px;
}
.title-fix {
    padding-top: 30px;
    padding-left: 30px;
    height: 40px;
}
h3 {
    font-size: 32px;
    font-weight: 400;
    color: #3b3d45;
    margin-block-start: 0;
    margin-block-end: 0;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
}
p {
    color: #707070;
    margin-block-start: 0;
    margin-block-end: 0;
    margin-top: 20px;
    font-size: 14px;
}
.el-button[data-v-0060bd44] {
    height: 64px;
    width: 330px;
    margin-left: 25px;
    font-size: 16px;
}
img {
    vertical-align: middle;
}
.el-row {
    margin-top: 70px;
}
.foot {
    margin-top: 20px;
    text-align: right;
}
a {
    text-decoration: none;
    font-size: 14px;
    cursor: pointer;
}
.code {
    width: 157px;
    height: 46px;
    float: right;
}
canvas#s-canvas {
    width: 114px;
    height: 40px;
}
.s-canvas[data-v-0060bd44] {
    height: 40px;
}
.return-fl {
    width: 40px;
    height: 40px;
    background: url("../assets/returnReg.png") no-repeat;
    float: left;
    margin-right: 22px;
}
.fl {
    float: left;
}
.el-form.demo-ruleForm {
    margin-top: 25px;
    padding: 0 40px;
}
.demo-ruleForm >>> .el-input__inner {
    height: 46px;
}
</style>