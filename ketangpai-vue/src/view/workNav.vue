<template>
    <div class="wrap">
        
        <div
            class="in-header"
            style="box-shadow:0px 1px 3px 0px rgba(0, 0, 0, 0.04);background: none repeat scroll 0% 0% #fff;"
        >
            <router-link :to="'/course/courseIndex/'+courseId+'/'+role" class="return">
                <i class="el-icon-back"></i>
                <span style="background:#2c58ab">{{course.courseName}}</span>
            </router-link>
            <div class="nav gWidth" id="third-nav" style="box-shadow: none">
                <router-link :to="'/sub/'+courseId+'/'+workId+'/'+role" class="active" v-if="isStu&&isActiveSub" >提交作业</router-link>
                 <router-link :to="'/sub/'+courseId+'/'+workId+'/'+role" v-if="isStu&&!isActiveSub" @click.native="changeActiveSub">提交作业</router-link>
                <router-link :to="'/stuWork/'+courseId+'/'+workId+'/'+role+'/all'" class="active" v-if="isTec&&isActiveGrade">学生作业</router-link>
                 <router-link :to="'/stuWork/'+courseId+'/'+workId+'/'+role+'/all'" v-if="isTec&&!isActiveGrade" @click.native="changeActiveGrade">学生作业</router-link>
                <router-link :to="'/workInfo/'+courseId+'/'+workId+'/'+role" class="active" v-if="isActiveInfo">作业详情</router-link>
                <router-link :to="'/workInfo/'+courseId+'/'+workId+'/'+role" v-if="!isActiveInfo" @click.native="changeActiveInfo">作业详情</router-link>
            </div>
            <ul class="nav-menu-right">
                <el-dropdown trigger="click" class="avatar" placement="bottom-end">
                    <el-avatar size="small" src style="margin-top:22px; cursor: pointer;"></el-avatar>
                    <el-dropdown-menu slot="dropdown" class="avatar-s">
                        <router-link to="/userInfo">
                            <el-dropdown-item
                                icon="iconfont icon-shezhi"
                                @click.native="changeActive('')"
                            >个人设置</el-dropdown-item>
                        </router-link>
                        <el-dropdown-item icon="iconfont icon-tuichudenglu1">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <el-dropdown trigger="click" class="message" placement="bottom">
                    <el-badge :value="9" class="item">
                        <span class="iconfont icon-icon-xiaoxi"></span>
                    </el-badge>
                    <el-dropdown-menu slot="dropdown" class="message-s">
                        <el-dropdown-item icon="el-icon-setting">个人设置</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-circle-close">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </ul>
        </div>
        <router-view></router-view>
    </div>
    
</template>
<script>
export default {
    data(){
        return{
            isActiveSub:"",
            isActiveInfo:"",
            isActiveGrade:"",
            course:{},
            isStu:"",
            isTec:"",
            courseId:"",
            workId:"",
            role:"",
        }
    },
    mounted(){  
        this.courseId=this.$route.params.courseId;
        this.workId=this.$route.params.workId;
        this.role=this.$route.params.role;
        this.filter="";
        this.getCourse(this.$route.params.courseId);
        if(this.$route.params.role==1){
            this.isTec=true;
        }
        if(this.$route.params.role==2){
            this.isStu=true;
        }
        var path=this.$route.path;
        path=path.substring(path.indexOf('/')+1,path.indexOf('/',path.indexOf('/')+1));
        if(path=='sub'){
            this.isActiveSub=true;
            this.isActiveInfo=false;
            this.isActiveGrade=false;
        }
        if(path=="workInfo"){
            this.isActiveSub=false;
            this.isActiveInfo=true;
            this.isActiveGrade=false;
        }
        if(path=="stuWork"){
            this.isActiveSub=false;
            this.isActiveInfo=false;
            this.isActiveGrade=true;
        }
    },
    methods: {
        changeActiveGrade(){
            this.isActiveSub=false;
            this.isActiveInfo=false;
            this.isActiveGrade=true;
        },
        changeActiveSub(){
            this.isActiveSub=true;
            this.isActiveInfo=false;
            this.isActiveGrade=false;
        },
        changeActiveInfo(){
            this.isActiveSub=false;
            this.isActiveInfo=true;
            this.isActiveGrade=false;
        },
        getCourse(courseId){
            this.$axios
                .get("api/course/showCourseDetail?id=" + courseId)
                .then(res => {
                    this.course = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        }
    },
}
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
    font-size: 14px;
}
.in-header {
    height: 72px;
    position: relative;
    z-index: 999;
}
.return {
    position: absolute;
    top: 24px;
    left: 2%;
    height: 32px;
    display: block;
    font-size: 0;
}
.in-header .return i {
    display: inline-block;
    width: 27px;
    height: 25px;
    vertical-align: middle;
}
.el-icon-back {
    font-size: 20px;
    color: #5f6368;
    font-weight: 900;
}
.return span {
    line-height: 32px;
    vertical-align: middle;
    margin-left: 14px;
    display: inline-block;
    height: 32px;
    border-radius: 16px;
    font-size: 14px;
    font-family: PingFangSC-Medium;
    font-weight: 500;
    padding: 0 10px;
    min-width: 80px;
    text-align: center;
}
.return span {
    color: #fff;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.nav {
    height: 72px;
    text-align: center;
    width: 930px;
    margin: auto;
}
.in-header .nav a.active {
    border-bottom: 4px solid #2c58ab;
    border-left: 2px solid transparent;
    border-right: 2px solid transparent;
    font-size: 16px;
    font-weight: 500;
    color: rgba(59, 61, 69, 1);
}
.in-header .nav a {
    min-width: 80px;
    height: 67px;
    position: relative;
    padding-left: 10px;
    padding-right: 10px;
    margin-left: 20px;
    font-size: 16px;
    font-weight: 400;
    color: rgba(95, 99, 104, 1);
}
.in-header .nav .item,
.in-header .nav a,
.in-header .nav span {
    line-height: 70px;
    display: inline-block;
    text-align: center;
}
a {
    text-decoration: none;
    outline: 0;
    cursor: pointer;
}
.in-header .nav-menu-right {
    position: absolute;
    right: 70px;
    top: 0;
    padding: 0;
}
.nav-menu-right {
    margin: 0;
}
.nav-menu-right {
    float: right;
}
.message-s {
    top: 50px !important;
}
.avatar-s {
    top: 50px !important;
}
.item >>> .el-badge__content.is-fixed {
    top: 22px;
    right: 14px;
}
.item {
    display: block !important;
    height: 72px;
}
.message {
    height: 72px;
    float: right;
    line-height: 72px;
}
.avatar {
    height: 72px;
    margin-left: 30px;
    float: right;
}
.iconfont.icon-icon-xiaoxi {
    cursor: pointer;
    font-size: 28px;
}
</style>