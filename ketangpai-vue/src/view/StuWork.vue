<template>
    <div class="wrap">
        <div class="page">
            <div class="head-title">
                <div class="homework-title">
                    <h2 style="float:left;">{{work.title}}</h2>
                </div>
                <div class="togsh">
                    <p class="fl">截至&nbsp;&nbsp;{{work.deadline}}</p>
                    <div class="titlefr">
                        <div class="search-bar">
                            <input id="search-txt" placeholder="学号，姓名" v-model="search" type="text" />
                            <a class="search-bar-a" @click="searchKey"></a>
                        </div>
                    </div>
                </div>
            </div>
            <div
                class="work-tips"
                data-step="1"
                data-intro="老师可依据此作业属性来筛选出所要批阅的学生"
                data-position="bottom"
            >
                <dl id="grade-classify" class="classify tify">
                    <dt>成绩：</dt>
                    <li id="unlimited" v-if="!allScore" @click="getall">不限</li>
                    <li v-if="allScore" class="cur">不限</li>
                    <el-checkbox-group v-model="scoreCheckList" @change.native="classifySearch">
                        <el-checkbox label="已批"></el-checkbox>
                        <el-checkbox label="未批"></el-checkbox>
                        <el-checkbox label="未交"></el-checkbox>
                    </el-checkbox-group>
                </dl>
            </div>
            <div class="workList">
                <el-table :data="stuWorks" style="width: 100%" :header-cell-style="cellStyle">
                    <el-table-column prop="schoolId" label="学号" sortable></el-table-column>
                    <el-table-column prop="name" label="姓名" sortable></el-table-column>
                    <el-table-column prop="score" label="成绩" sortable>
                        <template slot-scope="scope">
                            <span v-if="scope.row.score=='未交'" style="color:red">{{scope.row.score}}</span>
                            <span
                                v-if="scope.row.score!='未交'&&!isGrade"
                                @click="toGrade(scope.row.id)"
                            >{{scope.row.score}}</span>
                            <input
                                class="score"
                                v-if="isGrade&&scope.row.id==clickRow"
                                type="text"
                                v-model="scope.row.score"
                                @blur="toScore(scope.row.schoolId,scope.row.score,scope.row.isRead)"
                            />
                            <span v-if="scope.row.score!='未交'" style="color:#aaa">/{{work.score}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="date" label="提交状态" width="200" sortable>
                        <template slot-scope="scope">
                            <span v-if="scope.row.score=='未交'">--</span>
                            <span v-if="scope.row.score!='未交'">{{scope.row.date}}</span>&nbsp;
                            <span
                                v-if="scope.row.date>work.deadline"
                                style="color:#fff;background:red"
                            >超</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="isRead" label="批改次数" sortable>
                        <template slot-scope="scope">
                            <span v-if="scope.row.score=='未交'">--</span>
                            <span v-if="scope.row.score!='未交'">批阅{{scope.row.isRead}}次</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop label="操作">
                        <template slot-scope="scope">
                            <router-link
                                target="_blank"
                                :to="{name:'preview',params:{workId:work.id,id:scope.row.id,file:scope.row.file}}"
                                v-if="scope.row.score!='未交'"
                                class="intopiyue"
                            >进入批阅</router-link>
                            <span v-if="scope.row.score=='未交'">--</span>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            StuWorks_glo: [],
            stuWorks: [],
            work: {},
            isGrade: "",
            clickRow: "",
            search: "",
            scoreCheckList: [],
            allScore: "",
            allSubmit: ""
        };
    },
    mounted() {
        var id = this.$route.params.workId;
        this.getWork(id);
        if (this.scoreCheckList.toString() == "") {
            this.allScore = true;
        }
        if (this.$route.params.filter != "all") {
            this.scoreCheckList.push(this.$route.params.filter);
        } else {
            this.scoreCheckList = [];
        }
        this.initFilter(id);
    },
    watch: {
        allScore(val) {
            if (val == true) {
                this.stuWorks = this.StuWorks_glo;
            }
        },
        search(val) {
            var id = this.$route.params.workId;
            if (val == "") {
                this.getStuWorks(id);
            }
        },
        scoreCheckList(val) {
            if (val.length == 3 || val.length == 0) {
                val.length = 0;
                this.allScore = true;
            } else {
                this.allScore = false;
            }
        },
        submitCheckList(val) {
            if (val.length == 2 || val.length == 0) {
                val.length = 0;
                this.allSubmit = true;
            } else {
                this.allSubmit = false;
            }
        }
    },
    methods: {
        initFilter(id) {
            this.$axios
                .get("api/teacher/getAllStudents?id=" + id)
                .then(res => {
                    for (var i = 0; i < res.data.length; ++i) {
                        if (!res.data[i].score) {
                            res.data[i].score = "未批";
                        }
                        if (!res.data[i].date) {
                            res.data[i].score = "未交";
                        }
                    }
                    this.stuWorks = res.data;
                    this.StuWorks_glo = res.data;
                    var score = this.scoreCheckList;
                    var works = this.StuWorks_glo;
                    var res = [];
                    if (score.length > 0) {
                        for (var i = 0; i < score.length; ++i) {
                            if (score[i] == "未批") {
                                var w = works.filter(function(x, index) {
                                    return x.score == "未批";
                                });
                                for (var j = 0; j < w.length; ++j)
                                    res.push(w[j]);
                            }
                            if (score[i] == "已批") {
                                var w = works.filter(function(x, index) {
                                    return x.isRead > 0;
                                });
                                for (var j = 0; j < w.length; ++j)
                                    res.push(w[j]);
                                console.log(w);
                            }
                            if (score[i] == "未交") {
                                var w = works.filter(function(x, index) {
                                    return x.score == "未交";
                                });
                                for (var j = 0; j < w.length; ++j)
                                    res.push(w[j]);
                            }
                        }
                    } else {
                        res = works;
                    }
                    this.stuWorks = res;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        getall() {
            this.scoreCheckList = [];
            this.allScore = true;
        },
        toScore(schoolId, score, read) {
            var id = this.$route.params.workId;
            this.$axios
                .post(
                    "api/teacher/updateScore?id=" +
                        id +
                        "&score=" +
                        score +
                        "&isRead=" +
                        (read + 1) +
                        "&schoolId=" +
                        schoolId
                )
                .then(res => {
                    this.isGrade = false;
                    this.getStuWorks(id);
                    this.getWork(id);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        toGrade(index) {
            this.isGrade = true;
            this.clickRow = index;
            console.log("index");
        },
        getStuWorks(id) {
            this.$axios
                .get("api/teacher/getAllStudents?id=" + id)
                .then(res => {
                    for (var i = 0; i < res.data.length; ++i) {
                        if (!res.data[i].score) {
                            res.data[i].score = "未批";
                        }
                        if (!res.data[i].date) {
                            res.data[i].score = "未交";
                        }
                    }
                    this.stuWorks = res.data;
                    this.StuWorks_glo = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        getWork(id) {
            this.$axios
                .get("api/homework/getWork?id=" + id)
                .then(res => {
                    this.work = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        cellStyle({ row, column, rowIndex, columnIndex }) {
            if (rowIndex > 0) {
                //指定坐标
                return "background:pink";
            } else {
                return;
            }
        },
        searchKey() {
            var id = this.$route.params.workId;
            this.$axios
                .get("api/teacher/search?id=" + id + "&key=" + this.search)
                .then(res => {
                    for (var i = 0; i < res.data.length; ++i) {
                        if (!res.data[i].score) {
                            res.data[i].score = "未批";
                        }
                        if (!res.data[i].date) {
                            res.data[i].score = "未交";
                        }
                    }
                    this.stuWorks = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        classifySearch() {
            var score = this.scoreCheckList;
            var works = this.StuWorks_glo;
            console.log(this.StuWorks_glo);
            var res = [];
            if (score.length > 0) {
                for (var i = 0; i < score.length; ++i) {
                    if (score[i] == "未批") {
                        var w = works.filter(function(x, index) {
                            return x.score == "未批";
                        });
                        for (var j = 0; j < w.length; ++j) res.push(w[j]);
                    }
                    if (score[i] == "已批") {
                        var w = works.filter(function(x, index) {
                            return x.isRead > 0;
                        });
                        for (var j = 0; j < w.length; ++j) res.push(w[j]);
                        console.log(w);
                    }
                    if (score[i] == "未交") {
                        var w = works.filter(function(x, index) {
                            return x.score == "未交";
                        });
                        for (var j = 0; j < w.length; ++j) res.push(w[j]);
                    }
                }
            } else {
                res = works;
            }
            this.stuWorks = res;
        }
    }
};
</script>

<style scoped>
body,
dd,
div,
dl,
dt,
fieldset,
form,
h1,
h2,
h3,
h4,
h5,
h6,
img,
input,
li,
ol,
p,
table,
td,
textarea,
ul {
    margin: 0;
    padding: 0;
}
.workList >>> .el-table {
    border: 1px solid #eaeaea;
}
.work-tips {
    background: #fff;
    margin: 20px 30px;
    box-sizing: border-box;
    border: 1px solid #dcdcdc;
}
.classify {
    border-bottom: 1px dashed #eaeaea;
    font-size: 0;
}
.classify dt {
    color: #5a5a5a;
    line-height: 46px;
    height: 46px;
    width: 150px;
    border-right: 1px solid #dcdcdc;
    background: #f2f2f2;
    text-indent: 30px;
    font-size: 12px;
    margin-right: 36px;
    border-bottom: 1px dashed #eaeaea;
    margin-bottom: -1px;
}
.classify * {
    display: inline-block;
}
.classify li.cur {
    background: #357ae8;
    border-color: #357ae8;
    border-radius: 2px;
    cursor: default;
    color: #fff;
}
.classify li {
    width: 48px;
    height: 20px;
    line-height: 20px;
    margin-right: 30px;
    border: 1px solid transparent;
    cursor: pointer;
    font-size: 12px;
    text-align: center;
}
.classify >>> .el-checkbox__label {
    font-size: 12px;
}
li {
    list-style: none;
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
.page {
    margin: auto;
    background: #fff;
    border: 1px solid #c8c8c8;
    margin-top: 120px;
    margin-bottom: 60px;
    width: 1020px;
}
.head-title {
    padding: 39px 30px 27px;
    height: 80px;
    position: relative;
    border-bottom: 1px solid #c8c8c8;
}
.homework-title {
    display: inline-block;
    width: 100%;
}
.head-title h2 {
    font-size: 18px;
    color: #2d2d2d;
    font-weight: 400;
    display: inline-block;
}
.togsh {
    padding-top: 25px;
}
.head-title .togsh p {
    padding: 5px 20px;
    margin-right: 10px;
    background: rgba(0, 0, 0, 0.1);
}
.fl {
    float: left;
}
.head-title .titlefr {
    float: right;
    margin-left: 14px;
}
.search-bar {
    border: 1px solid #c8c8c8;
    width: 150px;
    padding: 4px 10px 4px 20px;
    float: right;
}
input,
select,
textarea {
    outline: 0;
    border: none;
    background: 0 0;
    cursor: text;
}
.search-bar input {
    width: 130px;
    font-size: 14px;
    color: #a9a9a9;
    line-height: 20px;
    height: 20px;
}
.search-bar input,
.search-bar-a {
    display: inline-block;
}
.search-bar-a {
    background: url(../assets/homesearch.png) -22px 0 no-repeat;
    width: 15px;
    height: 15px;
    vertical-align: middle;
}
.search-bar input,
.search-bar-a {
    display: inline-block;
}
a {
    text-decoration: none;
    outline: 0;
    cursor: pointer;
}
.workList {
    margin: 20px 30px 60px;
}
.score {
    width: 40px;
    border-bottom: 1.5px solid #357ae8;
}
.el-table >>> .el-table_1_column_2 {
    font-weight: 700 !important;
}
.el-table >>> .el-table_1_column_4,
.el-table >>> .el-table_1_column_5 {
    color: #aaaaaa !important;
}
.intopiyue {
    font-size: 16px;
    color: #4d90fe;
}
.intopiyue:hover {
    text-decoration: underline;
}
</style>