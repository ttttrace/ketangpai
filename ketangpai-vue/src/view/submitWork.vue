<template>
    <div class="wrap">
        <div class="myWork">
            <div class="gWidth">
                <div class="work-title clearfix">
                    <h3>{{work.title}}</h3>
                    <p>
                        截至日期：
                        <span>{{work.deadline}}</span>
                    </p>
                    <p>个人作业</p>
                </div>
                <div id="viewer-handup">
                    <div class="sc-tj-box">
                        <el-button type="primary" @click="submitWork" v-if="!isSubmit">提交</el-button>
                        <el-button
                            type="primary"
                            @click="showHandup"
                            v-if="isSubmit&&!showUpload&&!isScore"
                        >更新提交</el-button>
                        <el-button
                            type="primary"
                            @click="submitWork"
                            v-if="isSubmit&&showUpload&&!isScore"
                        >更新提交</el-button>
                        <div class="status fr">
                            <span class="wwc" v-if="!isSubmit&&!isScore">未完成</span>
                            <span class="wwc" v-if="isSubmit&&!isScore">已完成</span>
                            <span class="wwc" v-if="isScore">
                                成绩：
                                <span
                                    style="
    color: red;
    font-size: 20px;
"
                                >{{workStu.score}}</span>
                            </span>
                        </div>
                    </div>
                    <div class="homework-box">
                        <div class="sc-tj-box sc-tj-box-new" v-if="showUpload||!isSubmit">
                            <el-upload
                                class="upload-demo"
                                action="api/upload/upload"
                                :show-file-list="false"
                                multiple
                                :limit="6"
                                :on-success="handleSucess"
                                :on-exceed="handleExceed"
                            >
                                <div style="width: 79px;height: 58px;float: left;">
                                    <i class="el-icon-plus"></i>
                                </div>
                                <div class="shangchuan">
                                    <div class="webuploader-pick">添加作业文件</div>
                                    <span>支持各类文档、图片、代码、压缩包格式</span>
                                </div>
                            </el-upload>
                        </div>
                        <div class="files">
                            <div class="file" v-for="file in fileList" :key="file">
                                <div style="width: 350px;display: inline-block;">
                                    <svg
                                        class="icon"
                                        aria-hidden="true"
                                        style="height:50px;width:50px;float: left;"
                                    >
                                        <use
                                            :xlink:href="'#icon-'+file.slice(file.lastIndexOf('.')+1)"
                                        />
                                    </svg>
                                    <div class="filename">
                                        <div
                                            style="position: relative;top: 5px;width: 260px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"
                                        >
                                            <span
                                                @click="download(file)"
                                                style="color: #707070;"
                                            >{{file.slice(file.indexOf(")")+1)}}</span>
                                        </div>
                                        <div class="ysc">已上传</div>
                                        <div class="del" @click="delFile(file)" v-if="showUpload">删除</div>
                                        <div
                                            class="del"
                                            @click="download(file)"
                                            v-if="!showUpload"
                                        >下载</div>
                                    </div>
                                </div>
                            </div>
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
            fileList: [],
            work: {},
            isSubmit: "",
            workStu: {},
            showUpload: "",
            isScore: ""
        };
    },
    mounted() {
        this.getHomework(this.$route.params.workId);
        this.getfile();
    },
    methods: {
        showHandup() {
            this.showUpload = !this.showUpload;
        },
        getfile() {
            var id = localStorage.getItem("id");
            this.$axios
                .get(
                    "api/homework/getWorkStu?workId=" +
                        this.$route.params.workId +
                        "&id=" +
                        id
                )
                .then(res => {
                    if (res.data.file) {
                        res.data.file = res.data.file.split(",");
                    } else {
                        res.data.file = [];
                    }
                    if (res.data.submit) {
                        this.isSubmit = true;
                        this.showUpload = false;
                    }
                    if (!res.data.submit) {
                        this.isSubmit = false;
                        this.showUpload = true;
                    }
                    if (res.data.score) {
                        this.isScore = true;
                    }
                    this.workStu = res.data;
                    this.fileList = res.data.file;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        getHomework(id) {
            this.$axios
                .get("api/homework/getWork?id=" + id)
                .then(res => {
                    this.work = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        handleExceed(files, fileList) {
            this.$message.warning(
                `当前限制选择 6 个文件，本次选择了 ${
                    files.length
                } 个文件，共选择了 ${files.length + fileList.length} 个文件`
            );
        },
        delFile(file) {
            this.$axios
                .post("api/upload/removeFile?path=" + file)
                .then(res => {})
                .catch(err => {
                    console.log(err);
                });
            this.fileList.pop(file);
        },
        download(file) {
            window.open(file);
        },
        handleSucess(response, file, fileList) {
            this.fileList.push(file.response.url[0]);
        },
        submitWork() {
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
            var workId = this.$route.params.workId;
            var file = this.fileList.toString();
            var id = localStorage.getItem("id");
            if(file!=""){
                if (currentdate > this.work.deadline && this.work.canDelay == 0) {
                alert("老师已设置禁止超时提交！");
            } else {
                this.$axios
                    .post(
                        "api/homework/addHomeworkStu?workId=" +
                            workId +
                            "&file=" +
                            file +
                            "&date=" +
                            currentdate +
                            "&id=" +
                            id
                    )
                    .then(res => {
                        this.isSubmit = true;
                        this.getHomework(this.$route.params.workId);
                        this.getfile();
                    })
                    .catch(err => {
                        console.log(err);
                    });
            }
            }else{
                alert("未上传任何文件！")
            }
            
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
.myWork {
    padding-top: 120px;
    padding-bottom: 60px;
}
.gWidth {
    margin: auto;
    max-width: 1224px;
    min-width: 980px;
}
.work-title h3 {
    font-size: 18px;
    font-weight: 400;
    margin-bottom: 20px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    max-width: 650px;
}
.work-title p {
    font-size: 12px;
    padding: 0 5px;
    line-height: 20px;
    margin-right: 10px;
    margin-bottom: 40px;
    background-color: #f1f3f4;
    color: #5f6368;
    border-radius: 2px;
    float: left;
}
.mywork-page .work-title p span {
    margin-right: 8px;
}
.sc-tj-box {
    height: 40px;
    line-height: 40px;
}
.clearfix:after {
    content: "";
    display: table;
    clear: both;
}
.fr {
    float: right;
}
.homework-box {
    margin-top: 20px;
    border: 1px solid #e2e6ed;
    background: #fff;
    border-radius: 8px;
}
.sc-tj-box-new {
    width: 330px;
    height: 80px;
    border: 2px dashed #ccc;
    margin: 15px 0 24px 24px;
}
.el-icon-plus {
    width: 36px;
    height: 36px;
    line-height: 36px !important;
    background: #409eff;
    border-radius: 50%;
    display: block;
    text-align: center;
    font-size: 16px !important;
    color: #fff !important;
    margin: 22px 13px 0 30px;
    float: left;
    position: relative;
}
.shangchuan {
    width: 250px;
    float: left;
}
.webuploader-pick {
    cursor: pointer;
    font-size: 14px;
    margin-top: 20px;
    line-height: 26px;
    color: #1f2023;
    float: left;
    clear: right;
}
.shangchuan span {
    color: #999;
    font-size: 12px;
    display: block;
    line-height: 12px;
    float: left;
}
.el-button {
    width: 102px;
}
.files {
    padding: 10px 24px;
}
.file {
    padding-bottom: 10px;
    font-size: 14px;
}
.filename {
    display: inline-block;
    height: 50px;
    width: 255px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin-left: 10px;
}
.del {
    display: inline-block;
    position: relative;
    top: 10px;
    left: 180px;
    color: #409eff;
}
.del:hover {
    text-decoration: underline;
    cursor: pointer;
}
.file:hover span {
    color: #409eff;
    cursor: pointer;
}
.file span:hover {
    text-decoration: underline;
}
.ysc {
    color: #ababab;
    cursor: default;
    display: inline-block;
    position: relative;
    top: 10px;
}
</style>