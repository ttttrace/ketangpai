<template>
    <div class="wrap">
        <div class="uc-layer-top self">
            <div class="uc-avatar face-icon">
                <img id="user-avatar" />
                <a href="javascript:;" class="change-avatar avatar upload">更改头像</a>
                <div class="marks"></div>
            </div>
            <h1 class="uc-name">{{userForm.name}}</h1>
        </div>
        <div class="setting-page cWidth">
            <div class="item-change">
                <div class="setting-item">
                    <div class="options basic">
                        <h3 class="title">基本资料</h3>
                        <ul style="position: relative;">
                            <li>
                                <p>姓名：</p>
                                <div class="edit-box">
                                    <span v-if="!isEditBasic">{{userForm.name}}</span>
                                    <input
                                        v-model="basicForm.name"
                                        type="text"
                                        class="txts"
                                        name="name"
                                        placeholder="请输入姓名"
                                        v-if="isEditBasic"
                                    />
                                </div>
                                <a href="javascript:;" class="edit-btn" v-if="!isEditBasic" @click="showEditBasic">编辑资料</a>
                                <a href="javascript:;" class="exit-edit" v-if="isEditBasic" @click="cancleEditBasic">放弃编辑</a>
                            </li>
                            <li>
                                <p v-if="role=='学生'">学号：</p>
                                <p v-if="role=='教师'">工号：</p>
                                <div class="edit-box">
                                    <span v-if="!isEditBasic">{{userForm.tsid}}</span>
                                    <input
                                        type="text"
                                        name="stno"
                                        class="txts"
                                        placeholder="请输入学号"
                                        v-if="isEditBasic"
                                        v-model="basicForm.tsid"
                                    />
                                </div>
                            </li>
                            <li>
                                <p>学校：</p>
                                <div class="edit-box">
                                    <span v-if="!isEditBasic">{{userForm.school}}</span>
                                    <input
                                        type="text"
                                        name="school"
                                        class="txts"
                                        placeholder="请输入学校"
                                        v-if="isEditBasic"
                                        v-model="basicForm.school"
                                    />
                                </div>
                            </li>
                        </ul>
                        <a href="javascript:;" class="save-btn hide2" v-if="isEditBasic">保存</a>
                    </div>
                    <div class="options rolechange">
                        <h3 class="title">身份角色</h3>
                        <ul>
                            <li>
                                <p>角色：</p>
                                <div class="edit-box">
                                    <span v-if="!isEditRole">{{role}}</span>
                                    <div class="radio1 hide2" style="float:left;margin-right:15px;" v-if="isEditRole">
                                        <el-radio
                                            v-model="radio"
                                            label="学生"
                                            name="userrole"
                                            id="userrole1"
                                        >学生</el-radio>
                                    </div>
                                    <div class="radio2 hide2" style="float:left;" v-if="isEditRole">
                                        <el-radio
                                            v-model="radio"
                                            label="教师"
                                            name="userrole"
                                            id="userrole2"
                                        >教师</el-radio>
                                    </div>
                                </div>
                                <a href="javascript:;" class="edit-btn"  v-if="!isEditRole" @click="showEditRole">变更身份</a>
                                <a href="javascript:;" class="exit-edit"  v-if="isEditRole" @click="cancleEditRole">放弃变更</a>
                            </li>
                        </ul>
                        <a href="javascript:;" class="save-btn hide2"  v-if="isEditRole">变更</a>
                    </div>
                    <div class="options account">
                        <h3 class="title">账号设置</h3>
                        <ul>
                            <li>
                                <p>账号：</p>
                                <div class="edit-box">
                                    <span>{{userForm.id}}</span>
                                </div>
                            </li>
                            <li>
                                <p>邮箱：</p>
                                <div class="edit-box">
                                    <span>{{userForm.username}}</span>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="options pass">
                        <h3 class="title">密码</h3>
                        <ul>
                            <li v-if="!isEditPassword">
                                <p>密码：</p>
                                <div class="edit-box">
                                    <span>***********</span>
                                </div>
                                <a href="javascript:;" class="edit-btn"  v-if="!isEditPassword" @click="showEditPassword">更换密码</a>
                            </li>
                        </ul>
                        <div class="password hide"  v-if="isEditPassword">
                            <ul>
                                <li>
                                    <p>新密码：</p>
                                    <div class="edit-box">
                                        <input
                                            type="password"
                                            name="newpass"
                                            class="txts"
                                            placeholder="请输入新密码"
                                            v-model="newPassword"
                                        />
                                    </div>
                                    <a href="javascript:;" class="exit-edit" @click="cancleEditPassword">放弃更改</a>
                                </li>
                                <li>
                                    <p>确认密码：</p>
                                    <div class="edit-box">
                                        <input
                                            type="password"
                                            name="surepass"
                                            class="txts"
                                            placeholder="确认密码"
                                            v-model="surePassword"
                                        />
                                    </div>
                                </li>
                            </ul>
                            <a href="javascript:;" class="save-btn">保存</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            radio: "",
            isEditBasic:false,
            isEditRole:false,
            isEditPassword:false,
            basicForm:{},
            userForm:{},
            newPassword:"",
            surePassword:""
        };
    },
    mounted(){
        this.getUserInfo();
    },
    methods: {
        showEditBasic(){
            this.$set(this.basicForm,'name',this.userForm.name)
            this.$set(this.basicForm,'tsid',this.userForm.tsid)
            this.$set(this.basicForm,'school',this.userForm.school)
            this.isEditBasic=true;
        },
        showEditRole(){
            this.radio=this.role;
            this.isEditRole=true;
        },
        showEditPassword(){
            this.isEditPassword=true;
        },
        cancleEditBasic(){
            this.isEditBasic=false;
            this.basicForm={};
        },
        cancleEditRole(){
            this.radio=""
            this.isEditRole=false;
        },
        cancleEditPassword(){
            this.isEditPassword=false;
            this.newPassword="";
            this.surePassword="";
        },
        getUserInfo(){
            var id=localStorage.getItem('id');
            var role=localStorage.getItem('role');
            if(role==1){
                this.role="教师"
            }
            if(role==2){
                this.role="学生"
            }
            this.$axios
                .get(
                    "api/user/userInfo?id=" +
                        id,
                    { }
                )
                .then(res => {
                    this.userForm=res.data;
                })
                .catch(err => {
                    alert(err.response.data.msg);
                    console.log(err);
                });
            this.$axios
                .get(
                    "api/user/userInfo/username?id=" +
                        id,
                    { }
                )
                .then(res => {
                    this.$set(this.userForm, 'username', res.data)
                })
                .catch(err => {
                    alert(err.response.data.msg);
                    console.log(err);
                });
                
           
        }
    },
};
</script>
<style scoped>
.wrap {
    font-size: 14px;
}
.uc-layer-top.self {
    height: 210px;
    margin-top: 65px;
}
.uc-layer-top {
    height: 260px;
    background-color: #fff;
    border-bottom: 1px solid #c8c8c8;
    text-align: center;
    margin-bottom: 40px;
    overflow: hidden;
}
.uc-avatar.face-icon {
    position: relative;
}
.uc-avatar {
    width: 115px;
    margin: 45px auto 0;
    background-color: #fff;
}
.uc-avatar.face-icon img {
    border-radius: 50%;
}
.uc-avatar img {
    display: block;
    margin: 0 auto;
    height: 73px;
    width: 73px;
    border-radius: 50%;
}
img {
    border: none;
}
.uc-avatar.face-icon .change-avatar:hover {
    opacity: 1;
}
.uc-avatar.face-icon .change-avatar {
    position: absolute;
    left: 50%;
    bottom: 0;
    margin-left: -36px;
    border-radius: 50%;
    width: 73px;
    height: 73px;
    color: #fff;
    text-align: center;
    background: rgba(0, 0, 0, 0.3);
    line-height: 73px;
    opacity: 0;
    transition: all 0.5s ease;
}
a {
    text-decoration: none;
    outline: 0;
}
.uc-name {
    padding: 43px 0 10px;
    font-size: 28px;
    font-weight: 700;
    margin-top: -30px;
    line-height: 34px;
    height: 34px;
}
.setting-page {
    margin-top: 40px;
    margin-bottom: 60px;
    border: 1px solid #c8c8c8;
    padding-bottom: 60px;
    background: #fff;
}
.cWidth {
    margin-left: auto;
    margin-right: auto;
}
.item-change {
    min-height: 600px;
}
ul {
    margin: 0;
    padding: 0;
}
.item-change .setting-item {
    padding: 20px 120px 0;
}
.item-change .setting-item .options {
    margin-top: 20px;
}
.item-change .setting-item .options .title {
    font-size: 14px;
    color: #333;
    border-bottom: 1px solid #DCDCDC;
    padding-bottom: 20px;
    line-height: 1;
    margin: 0;
}
.item-change .setting-item .options ul {
    padding-top: 10px;
    position: relative;
}
.item-change .setting-item .options ul li {
    height: 32px;
    margin-top: 10px;
}
li {
    list-style: none;
}
.item-change .setting-item .options ul li p {
    float: left;
    min-width: 75px;
    line-height: 32px;
    color: #969696;
    margin: 0;
}
.item-change .setting-item .options ul li .edit-box {
    float: left;
    position: relative;
}
.item-change .setting-item .options ul li .edit-box span {
    display: inline-block;
    line-height: 32px;
    color: #595959;
}
.item-change .setting-item .options ul li .edit-box .txts {
    width: 172px;
    height: 30px;
    line-height: 30px\9;
    border: 1px solid #c8c8c8;
    color: #595959;
    padding: 0 14px;
    border-radius: 3px;
    font-size: 14px;
}
input, select, textarea {
    outline: 0;
    border: none;
    background: 0 0;
    cursor: text;
}
.item-change .setting-item .options ul li .edit-btn:hover {
    background: #e6e6e6;
    color: #2D2D2D;
}
.item-change .setting-item .options ul li .edit-btn {
    color: #818181;
    border: 1px solid #c8c8c8;
}
.item-change .setting-item .options ul li>a {
    float: right;
    min-width: 56px;
    height: 22px;
    text-align: center;
    line-height: 22px;
    font-size: 12px;
    border-radius: 3px;
    margin-top: 3px;
    padding-left: 5px;
    padding-right: 5px;
}
a {
    text-decoration: none;
    outline: 0;
    cursor: pointer;
}
.item-change .setting-item .options ul li .exit-edit:hover {
    background: #4d90fe;
    color: #FFF;
}
.item-change .setting-item .options ul li .exit-edit {
    color: #4d90fe;
    border: 1px solid #4d90fe;
}
.item-change .setting-item .options .save-btn {
    display: block;
    margin-left: 75px;
    width: 56px;
    height: 26px;
    border-radius: 3px;
    background: #4d90fe;
    color: #FFF;
    text-align: center;
    line-height: 26px;
    margin-top: 10px;
}
</style>
