<template>
    <div class="wrap">
        <div class="menu">
            <div class="el-menu" mode="horizontal">
                <a class="nav" @click="drawer = true">
                    <i class="iconfont icon-daohang"></i>
                </a>
                <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
                    <el-breadcrumb-item>
                        <router-link to="/headerNav">课堂</router-link>
                    </el-breadcrumb-item>
                    <el-breadcrumb-item>{{course.courseName}}</el-breadcrumb-item>
                </el-breadcrumb>
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
                <el-dropdown trigger="click" class="message" placement="bottom">
                    <el-badge :value="9" class="item">
                        <span class="iconfont icon-icon-xiaoxi"></span>
                    </el-badge>
                    <el-dropdown-menu slot="dropdown" class="message-s">
                        <el-dropdown-item icon="el-icon-setting">个人设置</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-circle-close">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
        <div id="course-header" class="topbox cl">
            <div class="topm cl">
                <h1 style="padding-top: 0;padding-bottom: 5px">
                    {{course.courseName}}
                    <div class="edit"></div>
                </h1>
                <h2>{{course.className}}</h2>
                <div class="codetip" style="height:15px"></div>
                <div>
                    <div class="codetip" style="display:inline-block;">
                        <i class="iconfont iconketangma2"></i> 加课码:
                    </div>
                    <div class="codetip" style="display:inline-block;">{{course.id}}</div>
                    <ul>
                        <li class="li5" v-if="isStudent">
                            <a>
                                <i class="iconfont el-icon-user-solid" ></i>同学{{stuNum}}
                            </a>
                        </li>
                        <li class="li5" v-if="isTeacher">
                            <a>
                                <i class="iconfont el-icon-user-solid" ></i>学生{{stuNum}}
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="banner cl coursebanner coursebannernew" style="position: relative; z-index: 2;">
            <el-menu class="inner-menu" mode="horizontal" background-color="#F1F3F4">
                <el-menu-item index="1">作业</el-menu-item>
                <el-menu-item index="2">资料</el-menu-item>
                <el-menu-item index="3">公告</el-menu-item>
            </el-menu>
        </div>
        <div class="homework-page">
            <div class="width980 clearfix">
                <div class="cWidth-title" v-if="isTeacher">
                    <div class="send-an" >
                        <a id="act1" @click="sendBox">发布个人作业</a>
                    </div>
                </div>
                <div class="send-box" id="send-box" v-if="showSendBox">
                    <div class="input-box">
                        <el-input type="text" placeholder="作业名称" v-model="homework.title"></el-input>
                        <div
                            class="input-cont"
                            style="border: 1px solid #ececec; border-radius: 4px; padding:10px; margin-top:20px;"
                        >
                            <editor-bar v-on:url="getImgs" v-model="detail" :isClear="isClear"></editor-bar>
                        </div>
                        <el-upload
                            class="upload-demo"
                            action="api/upload/upload"
                            multiple
                            :limit="6"
                            :on-success="handleSucess"
                            :on-remove="handleRemove"
                            :on-preview="handlePreview"                            
                            :on-exceed="handleExceed"
                        >
                            <i class="iconfont icon-fujian">
                                <span style="font-size:12px;">上传附件</span>
                            </i>
                        </el-upload>

                        <div class="jz-time clearfix" style="height:40px">
                            <p class="fl">截止日期：</p>
                            <div class="date-box fl">
                                <el-date-picker
                                    v-model="homework.deadline"
                                    type="datetime"
                                    placeholder="选择日期时间"
                                    value-format="yyyy-MM-dd hh:mm:ss"
                                ></el-date-picker>
                            </div>
                            <div class="time-box fl">
                                <div class="allowouttime">
                                    <el-switch
                                        inactive-color="#A0A0A0"
                                        class="allowouttimetip"
                                        style="display: block"
                                        v-model="homework.canDelay"
                                        active-text="若超时，禁止提交"
                                        inactive-text
                                    ></el-switch>
                                </div>
                            </div>
                        </div>
                        <div class="jz-time clearfix">
                            <p class="fl">满分值：</p>
                            <el-input
                                placeholder
                                style="width:60px;float:left"
                                v-model="homework.score"
                            ></el-input>
                        </div>
                    </div>
                    <div class="opt-cont">
                        <div class="opt-btn fr">
                            <el-button size="medium" plain @click="cancleAddwork">取消</el-button>
                            <el-button size="medium" type="primary" @click="addHomework">发布个人作业</el-button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="homework-cont width980 editor-page">
                <div class="homework-list clearfix" v-if="isStudent">
                    <div class="homework-box" v-for="homework in homeworkList" :key="homework.id">
                        <div class="announce-cont-box">
                            <div class="title clearfix">
                                <div class="work-type" data-time="1573971575">
                                    <span>个人作业</span>
                                    <span>{{homework.createTime}}</span>
                                </div>
                            </div>
                            <div class="announce-cont clearfix" style="position: relative;">
                                <div class="work-new-l" style="margin-right:145px;">
                                    <h3 class="work-title">
                                        <a @click="jumpToWorkInfo(homework.id)">{{homework.title}}</a>
                                    </h3>
                                    <div class="word">
                                        <div class="p" v-html="homework.content"></div>
                                    </div>
                                    <div class="annex">
                                        <ul class="clearfix">
                                            <div
                                                class="file"
                                                v-for="file in homework.file"
                                                :key="file"
                                                @mouseover="showDL"
                                                @mouseleave="closeDL"
                                            >
                                            <div @click="view(file)">
                                                <svg
                                                    class="icon"
                                                    aria-hidden="true"
                                                    style="height:80px;width:80px"
                                                >
                                                    <use
                                                        :xlink:href="'#icon-'+file.slice(file.lastIndexOf('.')+1)"
                                                    />
                                                </svg>
                                                <el-tooltip
                                                    class="item"
                                                    effect="light"
                                                    :content="file.slice(file.indexOf(')')+1)"
                                                    placement="top"
                                                >
                                                    <div
                                                        class="filename"
                                                    >{{file.slice(file.indexOf(")")+1)}}</div>
                                                </el-tooltip>
                                            </div>
                                                <div class="dl" @click="download(file)"><span v-if="DL">下载</span></div>
                                            </div>
                                        </ul>
                                    </div>
                                </div>
                                <br />
                                <div class="work-new-r" style="position:absolute" v-if="homework.unSubmit==0">
                                    <a class="sc-btn" @click="jumpTotjzy(homework.id)">上传作业</a>
                                </div>
                                <div class="work-new-r" style="position:absolute" v-if="homework.unSubmit==1">
                                    <a class="ytj-btn" style="color: #409eff;border:#409eff solid 1px;" @click="jumpTotjzy(homework.id)">已提交</a>
                                </div>
                            </div>
                            <div class="comment-new">
                                <p>
                                    截止日期：
                                    <span>{{homework.deadline}}</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="homework-list clearfix" v-if="isTeacher">
                    <div class="homework-box" v-for="homework in homeworkList" :key="homework.id">
                        <div class="announce-cont-box">
                            <div class="title clearfix">
                                <div class="work-type">
                                    <span>个人作业</span>
                                    <span>{{homework.createTime}}</span>
                                    <div class="opt">
                                        <a href="javascript:;" class="opt-btn">
                                            <el-dropdown trigger="click" placement="bottom-start">
                                                <i class="el-icon-more"></i>
                                                <el-dropdown-menu slot="dropdown">
                                                    <el-dropdown-item
                                                        @click.native="showWorkUpdate"
                                                    >编辑</el-dropdown-item>
                                                    <el-dropdown-item
                                                        @click.native="deleteHomework(homework.id)"
                                                    >删除</el-dropdown-item>
                                                </el-dropdown-menu>
                                            </el-dropdown>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="announce-cont clearfix" style="margin-bottom: 10px">
                                <div class="work-new-r fr">
                                    <div class="total-cont">
                                        <p class="score">
                                            <a @click="jumpTopy(homework.id,'已批')">{{homework.read}}</a>
                                            <a class="green" @click="jumpTopy('已批')">已批</a>
                                        </p>
                                        <p class="score">
                                            <a @click="jumpTopy(homework.id,'未批')">{{homework.unRead}}</a>
                                            <a class="grey" @click="jumpTopy('未批')">未批</a>
                                        </p>
                                        <p class="score">
                                            <a @click="jumpTopy(homework.id,'未交')">{{homework.unSubmit}}</a>
                                            <a class="red" @click="jumpTopy('未交')">未交</a>
                                        </p>
                                    </div>
                                </div>
                                <div class="work-new-l" style="margin-right:340px;">
                                    <h3 class="work-title">
                                        <a @click="jumpToWorkInfo(homework.id)">{{homework.title}}</a>
                                    </h3>
                                    <div class="word">
                                        <div class="p" v-html="homework.content"></div>
                                    </div>
                                    <div class="annex">
                                        <ul class="clearfix">
                                            <div
                                                class="file"
                                                v-for="file in homework.file"
                                                :key="file"
                                               @mouseover="showDL"
                                               @mouseleave="closeDL"
                                            >   
                                            <div @click="view(file)">
                                                <svg
                                                    class="icon"
                                                    aria-hidden="true"
                                                    style="height:80px;width:80px"
                                                >
                                                    <use
                                                        :xlink:href="'#icon-'+file.slice(file.lastIndexOf('.')+1)"
                                                    />
                                                </svg>
                                                <el-tooltip
                                                    class="item"
                                                    effect="light"
                                                    :content="file.slice(file.indexOf(')')+1)"
                                                    placement="top"
                                                >
                                                    <div
                                                        class="filename"
                                                    >{{file.slice(file.indexOf(")")+1)}}</div>
                                                </el-tooltip>
                                            </div>
                                                <div class="dl" @click="download(file)"><span v-if="DL">下载</span></div>
                                            </div>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="comment-new">
                                <p data-time="1576771140">
                                    截止日期：
                                    <span>{{homework.deadline}}</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <el-drawer
            :visible.sync="drawer"
            :direction="direction"
            :show-close="false"
            size="23%"
            class="leftNav"
        >
            <el-menu class="left-menu">
                <el-menu-item index="1" @click="jumptoCourse()">
                    <i class="iconfont icon-kecheng">
                        <span>课堂</span>
                    </i>
                </el-menu-item>
                <div class="ltitle">学习的课程</div>
                <el-menu-item
                    v-for="scourse in courseList"
                    :key="scourse.id"
                    @click.native="jumpToCourseIndex(scourse.id)"
                >
                    <i class="circle">{{scourse.courseName.slice(0,1)}}</i>
                    {{scourse.courseName}}
                </el-menu-item>
                <div class="ltitle" v-if="isTeacher">教授的课程</div>
                <el-menu-item
                    v-for="tcourse in TCourseList"
                    :key="tcourse.id"
                    @click.native="jumpToCourseIndex(tcourse.id)"
                >
                    <i class="circle">{{tcourse.courseName.slice(0,1)}}</i>
                    {{tcourse.courseName}}
                </el-menu-item>
            </el-menu>
        </el-drawer>
        <el-dialog :visible.sync="updateWorkDialog" title="修改作业">
            <div class="send-box" id="send-box" style="margin-top:0px">
                <div class="input-box">
                    <el-input type="text" placeholder="作业名称" v-model="homework.title"></el-input>
                    <div
                        class="input-cont"
                        style="border: 1px solid #ececec; border-radius: 4px; padding:10px; margin-top:20px;"
                    >
                        <editor-bar v-on:url="getImgs" v-model="detail" :isClear="isClear"></editor-bar>
                    </div>
                    <el-upload
                        class="upload-demo"
                        action="api/upload/upload"
                        :on-success="handleSucess"
                        :on-remove="handleRemove"
                        :on-preview="handlePreview"
                        multiple
                        :limit="6"
                        :on-exceed="handleExceed"
                    >
                        <i class="iconfont icon-fujian">
                            <span style="font-size:12px;">上传附件</span>
                        </i>
                    </el-upload>

                    <div class="jz-time clearfix" style="height:40px">
                        <p class="fl">截止日期：</p>
                        <div class="date-box fl">
                            <el-date-picker
                                v-model="homework.deadline"
                                type="datetime"
                                placeholder="选择日期时间"
                                value-format="yyyy-MM-dd hh:mm:ss"
                            ></el-date-picker>
                        </div>
                        <div class="time-box fl">
                            <div class="allowouttime">
                                <el-switch
                                    inactive-color="#A0A0A0"
                                    class="allowouttimetip"
                                    style="display: block"
                                    v-model="homework.canDelay"
                                    active-text="若超时，禁止提交"
                                    inactive-text
                                ></el-switch>
                            </div>
                        </div>
                    </div>
                    <div class="jz-time clearfix">
                        <p class="fl">满分值：</p>
                        <el-input
                            placeholder
                            style="width:60px;float:left"
                            v-model="homework.score"
                        ></el-input>
                    </div>
                </div>
                <div class="opt-cont">
                    <div class="opt-btn fr">
                        <el-button size="medium" plain @click="cancleAddwork">取消</el-button>
                        <el-button size="medium" type="primary" @click="addHomework">发布个人作业</el-button>
                    </div>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import EditorBar from "@/components/WangEditor.vue";
export default {
    data() {
        return {
            showSendBox: false,
            isClear: false,
            courseList: [],
            TCourseList: [],
            homeworkList: [{ homework: { file: [] } }],
            isStudent: "",
            isTeacher: "",
            course: {},
            detail: "",
            activeIndex: "1",
            drawer: false,
            direction: "ltr",
            imgurl: [],
            fileList: [],
            homework: {},
            workFile: [],
            updateWorkDialog: false,
            uwork: {},
            uimgurl: [],
            ufileList: [],
            stuNum:"",
            DL:"",
        };
    },
    components: {
        EditorBar
    },
    mounted() {
        var id = localStorage.getItem("id");
        this.getStudyCourse(id);
        if (this.$route.params.role == 1) {
            this.isTeacher = true;
            this.getTeachCourse(id);
            this.getHomeWork(this.$route.params.courseId);
        }
        if (this.$route.params.role == 2) {
            this.isStudent = true;
            this.getHomeWorkStu(this.$route.params.courseId,id);
        }
        this.getCourse(this.$route.params.courseId);
        this.getStuNum();
        console.log(this.homeworkList);
        //this.getState();
    },
    methods: {
        showDL(){
            this.DL=true;
        },
        closeDL(){
            this.DL=false;
        },
        jumpTopy(workId,filter){
            this.$router.push({name:"StuWork",params:{courseId:this.$route.params.courseId,workId:workId,role:this.$route.params.role,filter:filter}})
        },
        jumpToWorkInfo(workId){
            this.$router.push({name:"workInfo",params:{courseId:this.$route.params.courseId,workId:workId,role:this.$route.params.role}})
        },
        getStuNum(){
            this.$axios
                .get("api/course/getStuNum?courseId=" + this.$route.params.courseId, {})
                .then(res => {
                    this.stuNum = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        jumpTotjzy(workId){
            this.$router.push({name:"submitWork",params:{courseId:this.$route.params.courseId,workId:workId,role:this.$route.params.role}})
        },
        showWorkUpdate() {
            this.updateWorkDialog = true;
        },
        getStudyCourse(uid) {
            this.$axios
                .get("api/course/findAllCourseByUser?id=" + uid, {})
                .then(res => {
                    if (res) {
                        for (var i = 0; i < res.data.length; ++i) {
                            if (res.data[i].term) {
                                res.data[i].term = res.data[i].term.split(",");
                            } else {
                                res.data[i].term = [];
                            }
                        }
                        this.courseList = res.data;
                    }
                })
                .catch(err => {
                    alert(err.response.data.msg);
                    console.log(err);
                });
        },
        getTeachCourse(uid) {
            this.$axios
                .get("api/course/findAllTeacherCourse?id=" + uid)
                .then(res => {
                    if (res) {
                        for (var i = 0; i < res.data.length; ++i) {
                            if (res.data[i].term) {
                                res.data[i].term = res.data[i].term.split(",");
                            } else {
                                res.data[i].term = [];
                            }
                        }
                        this.TCourseList = res.data;
                    }
                })
                .catch(err => {
                    alert(err.response.data.msg);
                    console.log(err);
                });
        },
        getCourse(courseId) {
            this.$axios
                .get("api/course/showCourseDetail?id=" + courseId)
                .then(res => {
                    this.course = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        jumptoCourse() {
            this.$router.push({ name: "course" });
        },
        jumpToCourseIndex(id) {
            this.$router.push({
                name: "courseIndex",
                params: { courseId: id }
            });
            this.$router.go(0);
        },
        sendBox() {
            this.showSendBox = true;
        },
        getHomeWork(courseId) {
            this.$axios
                .get("api/homework/getHomeworks?courseId=" + courseId, {})
                .then(res => {
                    for (var i = 0; i < res.data.length; ++i) {
                        if (res.data[i].file) {
                            res.data[i].file = res.data[i].file.split(",");
                        } else {
                            res.data[i].file = [];
                        }
                    }
                    this.homeworkList = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        getHomeWorkStu(courseId,id){
            this.$axios
                .get("api/homework/getHomeworksStudent?courseId=" + courseId+"&id="+id, {})
                .then(res => {
                    for (var i = 0; i < res.data.length; ++i) {
                        if (res.data[i].file) {
                            res.data[i].file = res.data[i].file.split(",");
                        } else {
                            res.data[i].file = [];
                        }
                    }
                    this.homeworkList = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        download(file) {
            window.open(file);
        },
        deleteHomework(id) {
            this.$axios
                .post("api/homework/deleteWork?id=" + id)
                .then(res => {
                    this.$message({
                        showClose: true,
                        message: "作业已删除",
                        type: "success"
                    });
                     this.getHomeWork(this.$route.params.courseId)
                })
                .catch(err => {
                    this.$message({
                        showClose: true,
                        message: "作业删除失败",
                        type: "error"
                    });
                    console.log(err);
                });
               
        },
        cancleAddwork() {
            this.showSendBox = false;
            this.homework = {};
            this.detail = "";
            for (var i = 0; i < this.imgurl.length; ++i) {
                this.$axios
                    .post("api/upload/removeFile?path=" + this.imgurl[i])
                    .then(res => {})
                    .catch(err => {
                        alert(err.response.data.msg);
                        console.log(err);
                    });
            }
            for (var i = 0; i < this.fileList.length; ++i) {
                this.$axios
                    .post("api/upload/removeFile?path=" + this.fileList[i])
                    .then(res => {})
                    .catch(err => {
                        alert(err.response.data.msg);
                        console.log(err);
                    });
            }
            this.fileList = [];
            this.imgurl = [];
            console.log(this.fileList);
        },
        addHomework() {
            this.homework.courseId = this.course.id;
            this.homework.content = this.detail;
            var fileList = "";
            if (this.imgurl.toString() != "") {
                fileList = fileList + this.imgurl.toString();
            }
            if (
                this.fileList.toString() != "" &&
                this.imgurl.toString() != ""
            ) {
                fileList = fileList + "," + this.fileList.toString();
            }
            if (
                this.fileList.toString() != "" &&
                this.imgurl.toString() == ""
            ) {
                fileList = fileList + this.fileList.toString();
            }
            this.homework.file = fileList;
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month =
                date.getMonth() + 1 < 10
                    ? "0" + (date.getMonth() + 1)
                    : date.getMonth() + 1;
            var strDate =
                date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            var currentdate =
                date.getFullYear() +
                seperator1 +
                month +
                seperator1 +
                strDate +
                " " +
                date.getHours() +
                seperator2 +
                date.getMinutes() +
                seperator2 +
                date.getSeconds();
            this.homework.createTime = currentdate;
            this.$axios
                .post("api/homework/addHomework", this.homework)
                .then(res => {
                    this.showSendBox = false;
            this.getHomeWork(this.$route.params.courseId);
            this.imgurl=[];
            this.fileList=[];
            this.cancleAddwork();
                })
                .catch(err => {
                    console.log(err);
                });
            
        },
        getImgs(url) {
            alert(url);
            this.imgurl = url;
        },
        exit(){
            localStorage.clear();
            this.$router.push({name:"index"});
        },
        handleExceed(files, fileList) {
            this.$message.warning(
                `当前限制选择 6 个文件，本次选择了 ${
                    files.length
                } 个文件，共选择了 ${files.length + fileList.length} 个文件`
            );
        },
        handleRemove(file, fileList) {
            this.$axios
                .post("api/upload/removeFile?path=" + file.response.url)
                .then(res => {})
                .catch(err => {
                    alert(err.response.data.msg);
                    console.log(err);
                });
            this.fileList.pop(file.response.url);
        },
        handlePreview(file) {
            var url = file.response.url;
            window.open("http://127.0.0.1:8012/onlinePreview?url="+encodeURIComponent(url));
        },
        handleSucess(response, file, fileList) {
            this.fileList.push(file.response.url[0]);
        },
        view(file){
           window.open("http://127.0.0.1:8012/onlinePreview?url="+encodeURIComponent(file));
        }
    }
};
</script>

<style scoped>
.dl span:hover{
    text-decoration: underline;
}
.dl{
    font-family:'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
    font-size:12px;
    margin-top:10px;
    height: 16px;
}
.ytj-btn{
        background:#fff ;
        color: #409eff;
}
.el-dialog >>> .el-dialog__body {
    padding: 0px 20px 20px !important;
}
.file {
    width: 120px;
    display: inline-block;
    text-align: center;
}
.file:hover {
    color: #409eff;
    cursor: pointer;
}

.filename {
    width: 100px;
    font-size: 12px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin: auto;
}
.annex {
    height: auto;
    overflow: auto;
    max-height: inherit;
}
.annex {
    margin-top: 10px;
    overflow: hidden;
}
ul {
    margin: 0;
    padding: 0;
}
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
.menu {
    position: fixed;
    height: 72px;
    width: 100%;
    z-index: 999;
}
.el-menu {
    width: 100%;
    height: 100%;
    line-height: 73px;
    border: none;
}
.inner-menu {
    background: rgba(241, 243, 244, 1);
    border-radius: 0 0 8px 8px;
}
.inner-menu >>> .el-menu-item {
    height: 100%;
    line-height: 73px;
    font-size: 16px;
    background: rgba(241, 243, 244, 1);
    margin: 0px 20px;
    padding: 0px 25px;
}
.inner-menu >>> .el-menu-item:hover {
    background: rgba(241, 243, 244, 1) !important;
    color: #66b1ff;
}
.inner-menu >>> .el-menu-item:active {
    background: rgba(241, 243, 244, 1);
    color: #66b1ff;
}
.nav {
    padding: 0px 40px;
}
.nav .iconfont {
    font-size: 25px;
}
a {
    text-decoration: none;
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
.breadcrumb {
    display: inline-block;
    padding-bottom: 3px;
    font-family: PingFangSC-Regular;
    color: #606266 !important;
    font-weight: 400;
}
.breadcrumb a {
    color: #606266;
    font-weight: 400;
}
.message {
    height: 72px;
    float: right;
}
.avatar {
    height: 72px;
    margin-left: 30px;
    float: right;
    margin-right: 50px;
}
.iconfont.icon-icon-xiaoxi {
    margin-top: 22px;
    cursor: pointer;
    font-size: 28px;
}
.topbox {
    margin: 105px auto 0;
    background: url("../assets/30-full.png") 0 center no-repeat;
    width: 1224px;
    background-size: cover;
    height: 200px;
    border-radius: 8px 8px 0 0;
    padding: 48px 0 0 40px;
    box-sizing: border-box;
}
.topm h1 .edit {
    display: inline-block;
    width: 24px;
    height: 24px;
    background: url("../assets/edit-course.png") 0/24px no-repeat;
    cursor: pointer;
    margin-left: 15px;
}
.topm h1 {
    padding-top: 22px;
    font-size: 36px;
    color: #fff;
}
.topm h1,
.topm h2 {
    margin: 0;
    font-weight: 500;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.topm h2 {
    font-size: 20px;
    font-family: PingFang SC;
    color: rgba(255, 255, 255, 1);
    line-height: 28px;
    width: 600px;
}
.codetip + div .codetip {
    text-align: center;
    color: #fff;
    background: rgba(255, 255, 255, 0.4);
    border-radius: 2px;
    float: left;
    height: 24px;
    line-height: 24px;
    padding: 0 5px;
}
.topm ul li {
    margin-left: 10px;
    position: relative;
    background: rgba(255, 255, 255, 0.4);
    text-align: center;
    padding-left: 10px;
    padding-right: 10px;
    height: 24px;
    line-height: 24px;
}
.topm ul,
.topm ul li {
    display: inline-block;
}
.topm ul {
    margin: 0;
    padding: 0;
}
.topm ul li a {
    font-size: 12px;
    display: block;
    color: #fff;
    font-weight: lighter;
}
.topm ul li a:hover,
.toprx ul li a:hover {
    text-decoration: underline;
}
.coursebannernew {
    height: 72px;
    width: 1224px;
    background: rgba(241, 243, 244, 1);
    border-radius: 0 0 8px 8px;
    margin: 0 auto;
}
.leftNav >>> .el-drawer__header {
    display: none !important;
}
.left-menu {
    font-size: 14px;
    padding-top: 15px;
}
.left-menu .iconfont {
    font-size: 20px;
    color: #000;
}
.left-menu .iconfont:hover {
    color: #66b1ff;
}
.left-menu .iconfont span {
    padding-left: 30px;
    font-size: 13px;
    display: inline-block;
}
.left-menu >>> .el-menu-item {
    padding-left: 0 !important;
    height: 50px;
    line-height: 50px;
    margin: 0;
    padding: 0px 30px !important;
}
.left-menu >>> .el-menu-item:hover {
    color: #66b1ff;
}
.ltitle {
    height: 46px;
    color: #5f6368;
    font-size: 12px;
    line-height: 46px;
    padding-left: 25px;
    cursor: pointer;
}
.circle {
    display: inline-block;
    width: 32px;
    height: 32px;
    line-height: 32px;
    background-repeat: no-repeat;
    vertical-align: middle;
    margin-right: 20px;
    background-color: #2c58ab;
    border-radius: 50%;
    font-style: normal;
    text-align: center;
    color: #fff;
    font-size: 14px;
    cursor: pointer;
}
.circle:hover {
    color: #fff;
}
.homework-page {
    padding-bottom: 60px;
}
.homework-page .width980 {
    width: 1224px;
    margin-left: auto;
    margin-right: auto;
}
.homework-cont {
    margin-top: 20px;
}
.clearfix {
    zoom: 1;
}
.homework-cont .homework-list .homework-box {
    padding: 0 20px 0 40px;
    border: 1px solid #e2e6ed;
    border-radius: 8px;
    background: #fff;
    margin-bottom: 20px;
}
.homework-cont .homework-list .homework-box:hover {
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.08);
}
.announce-cont-box {
    padding-right: 30px;
}
.announce-cont-box {
    padding: 0 0 20px;
}
.homework-cont
    .homework-list
    .homework-box
    .announce-cont-box
    .title
    .work-type {
    padding: 21px 0 0;
}
.announce-cont-box .title .work-type {
    color: #aaa;
    font-size: 0;
    padding: 21px 0 10px;
    line-height: 30px;
}
.fl {
    float: left;
}
.announce-cont-box .title .work-type span:first-child {
    color: #5f6368;
    background: #f1f3f4;
    padding: 3px 5px;
    border-radius: 2px;
}
.announce-cont-box .title .work-type span {
    font-size: 14px;
    margin-right: 15px;
    color: #5f6368;
}
.homework-cont .homework-list .homework-box .announce-cont-box .work-new-l {
    height: auto;
    width: 988px;
}
.work-title a:hover {
    color: #4d90fe;
    text-decoration: underline;
}
.work-title:hover {
    color: #4d90fe;
}
.work-title {
    margin: 0;
}
.homework-cont .homework-list .homework-box .announce-cont-box .work-title a {
    line-height: 38px;
    padding-bottom: 0;
}
.announce-cont-box .work-title a {
    display: inline-block;
    font-size: 20px;
    font-weight: 400;
    line-height: 30px;
    padding-bottom: 5px;
    word-break: break-all;
    word-wrap: break-word;
}
.word {
    height: auto;
    overflow: auto;
    max-height: inherit;
}
.word {
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    display: -webkit-box;
    line-height: 30px;
    max-height: 100px;
    overflow: hidden;
    font-size: 14px;
    word-wrap: break-word;
    word-break: break-all;
}
.p {
    color: #707070;
    overflow: hidden;
    line-height: 1.8;
}
.work-new-r {
    bottom: 0;
    right: 0;
    margin-right: 25px;
        z-index: 20;
}
.sc-btn {
    background: #409eff;
}
.homework-cont .homework-list .homework-box .announce-cont-box .work-new-r > a {
    float: right;
    width: 80px;
    height: 30px;
    text-align: center;
    line-height: 30px;
    color: #fff;
    border-radius: 4px;
    font-size: 14px;
}
.comment-new {
    height: 20px;
    line-height: 20px;
}
p {
    padding-right: 10px;
    margin: 0;
}
.comment-new a,
.comment-new p {
    float: left;
    margin-right: 20px;
    color: #a0a0a0;
}
.comment-new p b {
    font-weight: 400;
    margin-left: 10px;
}
.cWidth-title {
    width: auto;
    height: auto;
    padding-top: 40px;
    overflow: hidden;
}
.send-an,
.send-ppt,
.send-weike {
    width: 136px;
    border: 1px solid #409eff;
    height: 40px;
    line-height: 40px;
    border-radius: 4px;
    text-align: center;
    background: #fff;
    margin-right: 16px;
    float: left;
}
.send-an a,
.send-ppt a,
.send-weike a {
    color: #409eff;
    display: block;
    cursor: pointer;
}
.opt {
    float: right;
    margin-top: -15px;
    position: relative;
}
.fr {
    float: right;
}
.work-new-r .total-cont {
    min-width: 290px;
    height: 66px;
    margin-top: 5px;
    margin-right: 0;
    border: none;
    background: #fff;
    padding: 0;
}
.total-cont p {
    margin: 0;
    font-size: 0;
    line-height: 20px;
    text-align: center;
    float: left;
}
.total-cont p:first-child:hover a:first-child {
    color: #32c76e;
}

.total-cont p a:first-child {
    font-size: 30px;
    height: 44px;
    line-height: 44px;
}
.total-cont p:hover a:first-child {
    color: #818181;
}
.total-cont p a {
    display: block;
    min-width: 80px;
    padding-left: 15px;
    color: #707070;
    padding-right: 15px;
}
.total-cont p a:last-child {
    font-size: 14px;
    height: 26px;
    line-height: 26px;
}
.total-cont .grey {
    color: #970002;
}
.total-cont p:last-child:hover a:first-child {
    color: #e03838;
}
#send-box {
    border: 1px solid #e2e6ed;
}
.send-box {
    margin-top: 20px;
    border-radius: 8px;
    background: #fff;
    padding: 20px 20px 60px;
}
.jz-time {
    margin-top: 18px;
}
.jz-time p {
    margin: 0;
    width: 75px;
    text-align: right;
    margin-right: 15px;
    line-height: 40px;
    color: #010000;
}
.time-box {
    height: 40px;
    position: relative;
    margin-right: 10px;
}
.allowouttime {
    float: left;
    margin-left: 60px;
}
.allowouttimetip {
    margin-top: 10px;
    height: 20px;
    float: left;
    margin-right: 20px;
}
.opt-cont {
    padding: 40px 0 0;
    font-size: 16px;
}
.p >>> p {
    margin: 0 !important;
    padding: 0;
}
</style>