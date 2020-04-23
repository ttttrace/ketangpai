<template>
    <div class="wrap">
        <div class="ktcon">
            <div class="ktcon1">
                <span class="ktcon1-p" v-if="isStudent">全部课程</span>
                <span class="ktcon1-p" v-if="isTeacher">所学课程</span>
                <el-dropdown trigger="click" placement="bottom" class="course-button">
                    <span>
                        <el-button type="primary" v-if="isTeacher">+ 创建/加入课程</el-button>
                    </span>
                    <span>
                        <el-button type="primary" v-if="isStudent" @click="showJoinCourse">+ 加入课程</el-button>
                    </span>
                    <el-dropdown-menu slot="dropdown" class="course-s">
                        <el-dropdown-item @click.native="showAddCourse">创建课程</el-dropdown-item>
                        <el-dropdown-item @click.native="showJoinCourse">加入课程</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <div class="ktcon1r">
                    <ul class="course-ul">
                        <li class="ktli1">
                            <i class="iconfont icon-paixu1">课程排序</i>
                        </li>
                        <li class="ktli2">
                            <i class="iconfont icon-guidang">归档管理</i>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="ktcon2">
                <div class="ktc" v-for="course in courseList" :key="course.id">
                    <div class="ktc-bg">
                        <strong>
                            <a
                                @click="jumpToClassIndex(course.id,'2')"
                                class="jumptoclass"
                            >{{course.courseName}}</a>
                            <span>{{course.className}}</span>
                        </strong>
                        <div class="invitecode">
                            <div class="qrcode">
                                <i class="iconfont icon-ico"></i>
                                <span class="code" style="margin-bottom:2px">加课码：{{course.id}}</span>
                            </div>
                        </div>
                        <span class="term">
                            {{course.term[0]}}
                            <br />
                            {{course.term[1]}}
                        </span>
                    </div>
                    <div class="homework">
                        <ul>
                            <li class="off">
                                <span>近期作业</span>
                            </li>
                            <li v-for="homework in course.homeworks" :key="homework.id">
                                <span>
                                    <router-link
                                        :to="'workInfo/'+course.id+'/'+homework.id+'/2'"
                                    >{{homework.title}}</router-link>
                                </span>
                            </li>
                        </ul>
                        <div class="ktc-foot">
                            <div class="user-avatar-area">
                                <el-avatar
                                    size="small"
                                    src
                                    style="float:left;margin-left:15px;margin-top:3.5px"
                                ></el-avatar>
                                <div class="teachername">{{course.teacher.name}}</div>
                            </div>
                            <el-dropdown trigger="click" class="more-menu" placement="top-end">
                                <div class="more">
                                    <span style="margin-right:-6px">更多</span>
                                    <i class="iconfont icon-gengduo"></i>
                                </div>
                                <el-dropdown-menu slot="dropdown" class="more-s">
                                    <el-dropdown-item>归档</el-dropdown-item>
                                    <el-dropdown-item
                                        @click.native="quitCourse(course.id,course.courseName)"
                                    >退课</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </div>
                </div>
                <div class="ktc-create">
                    <div class="ktc-create-bg"></div>
                    <div class="ktc-create-icon" @click="showJoinCourse">
                        <i class="el-icon-plus"></i>
                        加入课程
                    </div>
                </div>
                <div style="width:270px" v-for="(o,index) in divnum" :key="index"></div>
            </div>
            <div class="ktcon1" v-if="isTeacher">
                <span class="ktcon1-p">教授课程</span>
            </div>
            <div class="ktcon2" v-if="isTeacher">
                <div class="ktc" v-for="tcourse in TCourseList" :key="tcourse.id">
                    <div class="ktc-bg">
                        <strong>
                            <a
                                @click="jumpToClassIndex(tcourse.id,'1')"
                                class="jumptoclass"
                            >{{tcourse.courseName}}</a>
                            <span>{{tcourse.className}}</span>
                        </strong>
                        <div class="invitecode">
                            <div class="qrcode">
                                <i class="iconfont icon-ico"></i>
                                <span class="code" style="margin-bottom:2px">加课码：{{tcourse.id}}</span>
                            </div>
                        </div>
                        <span class="term">
                            {{tcourse.term[0]}}
                            <br />
                            {{tcourse.term[1]}}
                        </span>
                    </div>
                    <div class="homework">
                        <ul>
                            <li class="off">
                                <span>近期作业</span>
                            </li>
                            <li v-for="thomework in tcourse.homeworks" :key="thomework.id">
                                <span>
                                    <router-link
                                        :to="'workInfo/'+tcourse.id+'/'+thomework.id+'/2'"
                                    >{{thomework.title}}</router-link>
                                </span>
                            </li>
                        </ul>
                        <div class="ktc-foot">
                            <div class="user-avatar-area">
                                <el-avatar
                                    size="small"
                                    src
                                    style="float:left;margin-left:15px;margin-top:3.5px"
                                ></el-avatar>
                                <div class="teachername">{{tcourse.teacher.name}}</div>
                            </div>
                            <el-dropdown trigger="click" class="more-menu" placement="top-end">
                                <div class="more">
                                    <span style="margin-right:-6px">更多</span>
                                    <i class="iconfont icon-gengduo"></i>
                                </div>
                                <el-dropdown-menu slot="dropdown" class="more-s-t">
                                    <el-dropdown-item
                                        @click.native="showUpdateCourse(tcourse.id)"
                                    >编辑</el-dropdown-item>
                                    <el-dropdown-item>归档</el-dropdown-item>
                                    <el-dropdown-item
                                        @click.native="deleteCourse(tcourse.id,tcourse.courseName)"
                                    >删除</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </div>
                </div>
                <div class="ktc-create">
                    <div class="ktc-create-bg"></div>
                    <div class="ktc-create-icon" @click="showAddCourse">
                        <i class="el-icon-plus"></i>
                        创建课程
                    </div>
                </div>
                <div style="width:270px" v-for="(k,index) in tdivnum" :key="index"></div>
            </div>
        </div>
        <div>
            <el-dialog
                :visible.sync="addCourseDialog"
                title="新建课程"
                width="650px"
                class="addCourseDialog"
            >
                <el-form :model="newCourse" class="addCourseForm">
                    <el-form-item class="inputbox">
                        <label>课程名称:</label>
                        <el-input v-model="newCourse.courseName" placeholder="请输入课程名称"></el-input>
                    </el-form-item>
                    <el-form-item class="inputbox">
                        <label>班级名称:</label>
                        <el-input v-model="newCourse.className" placeholder="请输入班级名称（选填）"></el-input>
                    </el-form-item>
                    <el-form-item class="yearbox">
                        <label>选择学期:</label>
                        <el-select v-model="year">
                            <el-option
                                v-for="item in years"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                        <el-select v-model="term">
                            <el-option
                                v-for="item in terms"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="addCoursefooter">
                    <el-button @click="closeAddCourseDialog">取 消</el-button>
                    <el-button type="primary" @click="addCourse">创建</el-button>
                </span>
            </el-dialog>
        </div>
        <div>
            <el-dialog
                :visible.sync="updateCourseDialog"
                title="编辑课程"
                width="650px"
                class="addCourseDialog"
            >
                <el-form :model="oldCourse" class="addCourseForm">
                    <el-form-item class="inputbox">
                        <label>课程名称:</label>
                        <el-input v-model="oldCourse.courseName" placeholder="请输入课程名称"></el-input>
                    </el-form-item>
                    <el-form-item class="inputbox">
                        <label>班级名称:</label>
                        <el-input v-model="oldCourse.className" placeholder="请输入班级名称（选填）"></el-input>
                    </el-form-item>
                    <el-form-item class="yearbox">
                        <label>选择学期:</label>
                        <el-select v-model="year">
                            <el-option
                                v-for="(item) in years"
                                :key="item.index"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                        <el-select v-model="term">
                            <el-option
                                v-for="item in terms"
                                :key="item.index"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="addCoursefooter">
                    <el-button @click="closeUpdateCourseDialog">取 消</el-button>
                    <el-button type="primary" @click="updateCourse">确 定</el-button>
                </span>
            </el-dialog>
        </div>
        <div class="chuangjiankc" v-if="joinCourseDialog">
            <div class="chuangjiankctop">加入课程</div>
            <div class="chuangjiankccon">
                <input type="text" placeholder="请输入课程加课验证码" v-model="joinCourseId" />
            </div>
            <div class="chuangjiankcbot">
                <ul>
                    <li class="cjli1" @click="resetJoin">
                        <a class="btn btn-defalut">取消</a>
                    </li>
                    <li class="cjli2" @click="joinCourse(joinCourseId)">
                        <a class="btn disable">加入</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="v-modal" tabindex="0" style="z-index: 2000;" v-if="needdark"></div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            courseList: [],
            isStudent: "",
            isTeacher: "",
            divnum: "",
            addCourseDialog: false,
            TCourseList: [],
            homework: [],
            thomework: [],
            tdivnum: "",
            oldCourse: {
                id: "",
                courseName: "",
                className: "",
                teacherId: "",
                term: ""
            },
            newCourse: {
                id: "",
                courseName: "",
                className: "",
                teacherId: "",
                term: ""
            },
            joinCourseDialog: false,
            needdark: false,
            joinCourseId: "",
            updateCourseDialog: false,
            years: [],
            terms: [
                { value: "无限制", label: "无限制" },
                { value: "第一学期", label: "第一学期" },
                { value: "第二学期", label: "第二学期" }
            ],
            term: "",
            year: ""
        };
    },
    mounted() {
        this.loadCourse();
    },
    methods: {
        loadCourse() {
            var role = localStorage.getItem("role");
            var id = localStorage.getItem("id");
            var date = new Date();
            var startyear = 2011;
            var year = 2011;
            if (date.getMonth + 1 > 0 && date.getMonth + 1 < 7) {
                this.year = date.getFullYear() - 1 + "-" + date.getFullYear();
                this.term = "第二学期";
            } else {
                this.year = date.getFullYear() + "-" + (date.getFullYear() + 1);
                this.term = "第一学期";
            }
            for (var i = 0; i < date.getFullYear() - startyear + 2; ++i) {
                this.years.push({
                    value: year + "-" + (year + 1),
                    label: year + "-" + (year + 1)
                });
                year += 1;
            }
            this.$axios
                .get("api/course/findAllCourseByUser?id=" + id, {})
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
            this.divnum = 5 - ((this.courseList.length + 1) % 5);
            if (this.courseList.length < 5) {
                this.divnum = 4 - this.courseList.length;
            }
            if (role == 2) {
                this.isStudent = true;
            }
            if (role == 1) {
                this.isTeacher = true;
                this.$axios
                    .get("api/course/findAllTeacherCourse?id=" + id, {})
                    .then(res => {
                        if (res) {
                            for (var i = 0; i < res.data.length; ++i) {
                                if (res.data[i].term) {
                                    res.data[i].term = res.data[i].term.split(
                                        ","
                                    );
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
                this.tdivnum = 5 - ((this.TCourseList.length + 1) % 5);
                if (this.TCourseList.length < 5) {
                    this.tdivnum = 4 - this.TCourseList.length;
                }
            }
        },
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        showAddCourse() {
            this.addCourseDialog = true;
        },
        showUpdateCourse(courseId) {
            this.$axios
                .get("api/course/showCourseDetail?id=" + courseId)
                .then(res => {
                    this.oldCourse = res.data;
                    if (res.data.term) {
                        var terms = res.data.term.split(",");
                        this.term = terms[1];
                        this.year = terms[0];
                    }
                })
                .catch(err => {
                    console.log(err);
                });
            this.updateCourseDialog = true;
        },
        closeAddCourseDialog() {
            this.addCourseDialog = false;
            this.newCourse = {};
            this.term = "";
            this.year = "";
        },
        closeUpdateCourseDialog() {
            this.updateCourseDialog = false;
            this.oldCourse = {};
            this.term = "";
            this.year = "";
        },
        showJoinCourse() {
            this.joinCourseDialog = true;
            this.needdark = true;
        },
        resetJoin() {
            this.joinCourseDialog = false;
            this.needdark = false;
            this.joinCourseId = "";
        },
        addCourse() {
            var id = localStorage.getItem("id");
            this.$set(this.newCourse, "teacherId", id);
            this.$set(this.newCourse, "term", this.year + "," + this.term);
            while (1) {
                var courseId = Math.random()
                    .toString(36)
                    .substr(2, 6);
                this.$axios
                    .get("api/course/showCourseDetail?id=" + courseId)
                    .then(res => {
                        if (res.data == null || res.data == "") {
                            courseId = courseId;
                        }
                    })
                    .catch(err => {
                        console.log(err);
                    });
                this.$set(this.newCourse, "id", courseId);
                if (this.newCourse.id != "") {
                    console.log("rrr");
                    break;
                }
            }
            this.$axios
                .post("api/course/addCourse", this.newCourse)
                .then(res => {
                    this.$message({
                        showClose: true,
                        message: "课程已创建",
                        type: "success"
                    });
                          this.closeAddCourseDialog();
            this.loadCourse();
                })
                .catch(err => {
                    this.$message({
                        showClose: true,
                        message: "课程创建失败",
                        type: "error"
                    });
                    console.log(err);
                });
      
        },
        updateCourse() {
            this.$set(this.oldCourse, "term", this.year + "," + this.term);
            this.$axios
                .post("api/course/updateCourse", this.oldCourse)
                .then(res => {
                    this.$message({
                        showClose: true,
                        message: "课程已修改",
                        type: "success"
                    });
                })
                .catch(err => {
                    this.$message({
                        showClose: true,
                        message: "课程修改失败",
                        type: "error"
                    });
                    console.log(err);
                });
            this.closeUpdateCourseDialog();
            this.loadCourse();
        },
        quitCourse(courseId, courseName) {
            var id = localStorage.getItem("id");
            this.$confirm(
                "确认要退课“<span style=color:#66b1ff>" +
                    courseName +
                    "</span>”么？",
                "提示",
                {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }
            )
                .then(() => {
                    this.$axios
                        .post(
                            "api/course/quitCourse?courseId=" +
                                courseId +
                                "&studentId=" +
                                id
                        )
                        .then(res => {
                            this.$message({
                                showClose: true,
                                message: "退课成功",
                                type: "success"
                            });
                        })
                        .catch(err => {
                            this.$message({
                                showClose: true,
                                message: "退课失败",
                                type: "error"
                            });
                            console.log(err);
                        });
                    this.loadCourse();
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消退课"
                    });
                });
        },
        deleteCourse(courseId, courseName) {
            var id = localStorage.getItem("id");
            this.$confirm(
                "确认要删除“<span style=color:#66b1ff>" +
                    courseName +
                    "</span>”课么？",
                "提示",
                {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }
            )
                .then(() => {
                    this.$axios
                        .post(
                            "api/course/deleteCourse?courseId=" +
                                courseId +
                                "&teacherId=" +
                                id
                        )
                        .then(res => {
                            this.$message({
                                showClose: true,
                                message: "退课成功",
                                type: "success"
                            });
                            this.loadCourse();
                        })
                        .catch(err => {
                            this.$message({
                                showClose: true,
                                message: "退课失败",
                                type: "error"
                            });
                            console.log(err);
                        });
                    
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消删除课程"
                    });
                });
        },
        joinCourse(courseId) {
            var id = localStorage.getItem("id");
            this.$axios
                .get("api/course/showCourseDetail?id=" + courseId)
                .then(res => {
                    if (res.data) {
                        this.$axios
                            .post(
                                "api/course/joinCourse?courseId=" +
                                    courseId +
                                    "&studentId=" +
                                    id
                            )
                            .then(res => {
                                this.$message({
                                    showClose: true,
                                    message: "成功加入课程",
                                    type: "success"
                                });
                                this.joinCourseDialog = false;
                                this.needdark = false;
                                this.loadCourse();
                            })
                            .catch(err => {
                                this.$message({
                                    showClose: true,
                                    message: "加入课程失败",
                                    type: "error"
                                });
                                console.log(err);
                            });
                    }else{
                        alert("课堂不存在！")
                        this.resetJoin();
                    }
                })
                .catch(err => {
                    console.log(err);
                });
        },
        jumpToClassIndex(id, role) {
            this.$router.push({
                name: "courseIndex",
                params: { courseId: id, role: role }
            });
        }
    },
    created() {
        document.title = "课堂-课堂派";
    }
};
</script>

<style scoped>
.gd2 {
    padding-left: 70px;
    padding-right: 70px;
}
.bb-div {
    position: absolute;
    left: 0;
    top: 0;
    padding: 30px 70px;
    width: 670px;
}
.gpdl1 {
    float: left;
    width: 300px;
    -webkit-box-shadow: 0 0.1rem 0.2rem rgba(0, 0, 0, 0.12),
        0 0 0.1rem rgba(0, 0, 0, 0.12);
    box-shadow: 0 0.1rem 0.2rem rgba(0, 0, 0, 0.12),
        0 0 0.1rem rgba(0, 0, 0, 0.12);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    background-clip: padding-box;
}
dl {
    position: relative;
    margin: 15px 10px;
}
dt {
    width: 268px;
    clear: both;
    height: 72px;
    padding: 24px 16px 14px;
}
dt strong {
    font-size: 20px;
    font-weight: lighter;
    color: #fff;
    height: 26px;
    line-height: 26px;
}
dt strong a {
    display: block;
    color: #fff;
    overflow: hidden;
    width: 240px;
}
.v-modal {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    opacity: 0.5;
    background: #000;
    z-index: 2000;
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
}
.course-s {
    top: 129px !important;
    width: 130px;
}
.course-button {
    float: right;
}
.ktcon {
    clear: both;
    padding: 85px 60px 100px;
}
.ktcon1 {
    height: 36px;
    clear: left;
    padding-bottom: 12px;
    position: relative;
    z-index: 1;
    justify-content: flex-start;
    box-shadow: 0px 1px 0px 0px rgba(226, 230, 237, 1);
    margin-bottom: 20px;
    padding-top: 20px;
}
.ktcon1-p {
    display: block;
    float: left;
    height: 36px;
    line-height: 36px;
    color: rgba(59, 61, 69, 1);
    font-size: 14px;
}
.ktcon1 >>> .el-button {
    height: 36px;
    padding: 11px 15px;
}
.message-s >>> .popper__arrow {
    display: none !important;
}
.course-s >>> .popper__arrow {
    display: none !important;
}
.more-s >>> .popper__arrow {
    display: none !important;
}
.more-s-t >>> .popper__arrow {
    display: none !important;
}
.ktcon1r {
    width: 300px;
    height: 34px;
    line-height: 34px;
    float: right;
}
.ktcon1r ul {
    float: right;
    display: flex;
    align-items: center;
    height: 34px;
    line-height: 34px;
    margin: 0;
}
.ktcon1r ul li {
    float: left;
    padding-left: 27px;
}
.ktcon1r ul li.ktli1 {
    display: inline;
    line-height: 16px;
    color: #78787a;
    font-size: 14px;
}
.ktcon1r ul li.ktli2 {
    line-height: 16px;
    margin: 0 24px 0 0;
    display: inline;
    color: #78787a;
    font-size: 14px;
}
.icon-paixu1 {
    cursor: pointer;
}
.icon-paixu1:hover {
    color: #2d2d2d;
}
.icon-guidang:hover {
    color: #2d2d2d;
}
.icon-guidang {
    cursor: pointer;
}
.ktcon2 {
    clear: left;
    margin-top: 48px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}
.ktc-create {
    width: 270px;
    margin-bottom: 24px;
    border: 1px solid #e2e6ed;
    border-radius: 8px;
    display: block;
}
.ktc {
    width: 270px;
    margin-bottom: 24px;
    border: 1px solid #e2e6ed;
    border-radius: 8px;
    color: rgba(31, 32, 35, 1);
}
.ktc-bg {
    height: 129px;
    background: url("../assets/30.png");
    border-radius: 4px 4px 0 0;
    background-size: cover;
    clear: both;
    height: 95px;
    padding-top: 18px;
    padding-bottom: 14px;
}
.ktc-create-bg {
    height: 129px;
    background: url("../assets/create-course.png");
    border-radius: 4px 4px 0 0;
    background-size: cover;
}
.ktc-create-icon {
    height: 141px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}
strong {
    display: block;
    font-size: 20px;
    font-weight: lighter;
    color: #fff;
    height: 62px;
    margin-left: 18px;
    margin-right: 48px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
strong a {
    color: #fff;
    font-size: 20px;
    line-height: 30px;
}
strong a:hover {
    text-decoration: underline;
}
a {
    text-decoration: none;
    cursor: pointer;
}
strong > span {
    display: block;
    font-size: 16px;
    font-family: PingFangSC-Medium;
    font-weight: 500;
    color: rgba(255, 255, 255, 1);
    line-height: 28px;
    overflow: hidden;
}
.invitecode {
    height: 22px;
    background: rgba(255, 255, 255, 0.2);
    border-radius: 2px;
    font-size: 12px;
    font-family: PingFang-SC-Medium;
    font-weight: 500;
    color: rgba(255, 255, 255, 1);
    line-height: 22px;
    text-align: center;
    margin-left: 18px;
    padding-left: 2px;
    padding-right: 4px;
    margin-top: 13px;
    display: inline-block;
}
.qrcode {
    display: block;
    float: left;
}
.icon-ico {
    display: block;
    float: left;
}
.code {
    display: block;
    float: left;
}
.term {
    float: right;
    font-size: 12px;
    font-family: PingFangSC-Medium;
    font-weight: 500;
    color: rgba(255, 255, 255, 1);
    padding-right: 14px;
    opacity: 0.5;
    display: flex;
    align-items: flex-end;
    height: 30px;
}
.homework {
    background: #fff;
    clear: both;
    height: 141px;
    border-radius: 0 0 8px 8px;
}
.homework ul {
    margin-top: 15px;
    padding: 0 16px;
    height: 105px;
    border-bottom: 1px solid rgba(226, 230, 237, 1);
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-bottom: 0;
}
.homework ul li {
    height: 30px;
    line-height: 30px;
    width: 100%;
    overflow: hidden;
    font-size: 14px;
    color: rgba(31, 32, 35, 1);
    font-family: PingFangSC-Medium;
    font-weight: 500;
}

li,
ul {
    list-style: none;
}
.off span {
    font-size: 12px;
    font-family: PingFangSC-Medium;
    font-weight: 500;
    color: rgba(95, 99, 104, 1);
}
.homework ul li a:hover {
    color: #1da3c5;
    border-bottom: 1px solid #1da3c5;
}
.homework ul li a {
    font-size: 14px;
    color: rgba(31, 32, 35, 1);
    font-family: PingFangSC-Medium;
    font-weight: 500;
}
.ktc-foot {
    height: 35px;
    line-height: 35px;
}
.teachername {
    float: left;
    margin-left: 10px;
    font-size: 12px;
    color: rgba(95, 99, 104, 1);
    font-family: PingFangSC-Medium;
    font-weight: 500;
}
.more-menu {
    float: right;
}
.more {
    color: #2c5bab;
    float: right;
    font-size: 12px;
    cursor: pointer;
}
.addCourseForm {
    margin-top: -45px;
}
.addCourseForm .inputbox:focus-within {
    border-bottom: 2px solid #66b1ff;
}
.addCourseForm .inputbox {
    margin-top: 26px;
    height: 38px;
    border-bottom: 1px solid #c8c8c8;
    box-sizing: border-box;
}
.addCourseForm .inputbox >>> .el-input {
    width: 450px;
}
.addCourseForm .inputbox >>> .el-input__inner {
    border: none;
    background: transparent;
}
.addCourseDialog >>> .el-dialog__footer {
    padding: 20px 20px 20px;
    background: #f5f5f5;
}
.yearbox >>> .el-input {
    width: 270px;
}
.chuangjiankc {
    display: block;
    width: 295px;
    height: 210px;
    margin-left: -134px;
    margin-top: -105px;
    background: #fff;
    border: 0;
}
.chuangjiankc,
.huifukt {
    z-index: 2101;
    position: fixed;
    left: 50%;
    top: 50%;
}
.chuangjiankctop {
    color: #a9a9a9;
    font-size: 18px;
    border-bottom: 1px solid #ececec;
    height: 66px;
    line-height: 66px;
    padding-left: 28px;
}
.chuangjiankccon {
    padding: 20px 25px;
}
.chuangjiankccon input:focus {
    border-bottom: 1px solid #1da3c5;
}
.btn.disable {
    display: block;
    border-radius: 3px;
    width: 86px;
    height: 38px;
    line-height: 38px;
    text-align: center;
    font-size: 16px;
    color: #fff;
    background: #409eff;
}
.chuangjiankccon input {
    padding-top: 9px;
    padding-bottom: 9px;
    color: #2d2d2d;
    font-size: 14px;
    width: 100%;
    border: 0;
    height: 14px;
    line-height: 14px;
    border-bottom: 1px solid #ececec;
    outline: none;
}
.chuangjiankcbot {
    position: relative;
    height: 68px;
    background: #fff;
}
.chuangjiankcbot ul {
    text-align: center;
    padding-top: 12px;
    padding-left: 30px;
    padding-right: 15px;
}
.chuangjiankcbot ul li {
    float: left;
    padding-left: 10px;
}
.chuangjiankcbot ul li.cjli1 a {
    display: block;
    width: 86px;
    height: 38px;
    line-height: 38px;
    text-align: center;
    font-size: 16px;
    color: #818181;
    border-radius: 3px;
}
.btn {
    display: block;
    border-radius: 3px;
    min-width: 86px;
    height: 38px;
    line-height: 38px;
    text-align: center;
    font-size: 16px;
    padding-left: 5px;
    padding-right: 5px;
    cursor: pointer;
}
</style>