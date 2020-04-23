<template>
    <div class="wrap">
        <div class="wt-header">
            <div class="header-fl">
                <p class="tname text-overflow">{{work.name}}</p>
                <el-dropdown class="item-box2" trigger="click" placement="bottom-start">
                    <span><div class="default">{{currentFile.slice(currentFile.indexOf(")")+1)}}</div><i class="el-icon-arrow-down el-icon--right"></i></span>
                    <el-dropdown-menu slot="dropdown" class="item" style="left:30px;top:73px" >
                        <el-dropdown-item v-for="file in files" :key="file">
                            <a class="downloadCurrentFile" @click="pre(file)">{{file.slice(file.indexOf(")")+1)}}</a>
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
            <div class="header-fr">
                <div class="edstate">
                    <label for="score">成绩：</label>
                    <input type="text" value placeholder v-model="work.score"  @blur="toScore()"/>
                </div>
                 <el-dropdown class="download_drop_btn "  trigger="click" placement="bottom">
                    <div class="xiazai"></div>
                    <el-dropdown-menu slot="dropdown" class="download">
                        <el-dropdown-item>
                            <a class="downloadCurrentFile" :href="currentFile">下载当前文件</a>
                        </el-dropdown-item>
                        <el-dropdown-item>
                            <a class="downloadAllFile" @click="downloadFiles(files)">下载所有文件</a>
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
        <iframe v-loading="load" id="pdfview" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" class="main" :src=url>
        </iframe>
    </div>
</template>
<script>
export default {
    data(){
        return{
            files:[],
            work:{},
            url:"",
            currentFile:"",
            load:"",
        }
    },
    mounted(){
        var a=this.$route.params.file;
        if(a){
            a=a.split(",");
            this.files=a;
            this.currentFile=a[0];
        }else{
            this.files=a;
            this.currentFile=a;
        }
        this.getWork();
        this.url="http://127.0.0.1:8012/onlinePreview?url="+encodeURIComponent(this.currentFile);
        console.log(this.files)
    },
    methods: {
        toScore() {
            var id = this.$route.params.workId;
            this.$axios
                .post(
                    "api/teacher/updateScore?id=" +
                        id +
                        "&score=" +
                        this.work.score +
                        "&isRead=" +
                        (this.work.isRead + 1) +
                        "&schoolId=" +
                        this.work.schoolId
                )
                .then(res => {
                })
                .catch(err => {
                    console.log(err);
                });
        },
        getWork(){
            this.$axios
                .get("api/homework/getWorkStu?workId=" + this.$route.params.workId+"&id="+ this.$route.params.id)
                .then(res => {
                    this.work = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        pre(file){
            this.url="http://127.0.0.1:8012/onlinePreview?url="+encodeURIComponent(file);
            this.load=true;
            this.currentFile=file;
        },
        downloadFiles(files){
            for(var i=0;i<files.length;++i){
                this.download(files[i])
            }
        },
        download(file){
            console.log(file)
            window.open(file);
        }
    },
}
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
a{
    text-decoration: none;
    color: #FFF;
}
.item a{
    color: #2c2c2c;
}
.main{
    width:100%;
    height:100%;
    margin-top: 73px;
}
.el-icon-arrow-down{
        position: relative;
    top: -6px;
    font-size: 16px;
    color: #C8C8C8;
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
.wt-header {
    height: 73px;
    background: #333;
    position: fixed;
    width: 100%;
    left: 0;
    top: 0;
}
.header-fl {
    width: 300px;
    height: 73px;
    margin-left: 34px;
}
.tname {
    color: #FFF;
    font-size: 16px;
    line-height: 34px;
    padding-top: 7px;
}
.text-overflow {
    white-space: nowrap;
    overflow: hidden;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
}
 .item-box2 {
    width: 330px;
    height: 32px;
    line-height: 32px;
    position: relative;
    cursor: pointer;
}
.item{
    transform-origin: center top;
    z-index: 2005;
    position: fixed;
    top: 73px;
    left: 30px;
    width: 320px;
    max-height: 400px;
    background: #FFF;
    border: 1px solid #bdbdbd;
    padding: 10px 0;
    box-shadow: none;
    overflow: auto;
    margin-top: 0;
    border-radius: 0;
}
.item >>>.el-dropdown-menu__item{
        color: #2c2c2c;
    display: block;
    padding-left: 25px;
    padding-right: 25px;
    height: 40px;
    line-height: 40px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.item >>>.el-dropdown-menu__item:hover{
    background: #f6f6f6;
}
.item>>>.popper__arrow{
    display: none;
}
.download{
    transform-origin: center top;
    z-index: 2011;
    background-color: rgb(0, 0, 0);
    min-width: 90px;
    padding: 12px;
    color: rgb(255, 255, 255);
    position: fixed;
    top: 47px;
    left: 1416px;
    border: none;
    border-radius: 0;
}
.download>>>.el-dropdown-menu__item{
    color: #FFF;
}
.download>>>.el-dropdown-menu__item:hover{
    background: #000;
}
.download>>>.popper__arrow{
    display: none;
}
.default {
    display: inline-block;
    height: 22px;
    line-height: 22px;
    vertical-align: top;
    color: #fff;
    margin-right: 10px;
}
.header-m {
    height: 34px;
    position: absolute;
    top: 50%;
    margin-top: -16px;
    left: 50%;
    margin-left: -102px;
}
.header-fr {
    width: 280px;
    height: 58px;
    position: absolute;
    right: 0;
    top: 15px;
}
 .edstate {
    float: left;
    width: 132px;
    height: 39px;
    margin-left: 10px;
    background-color: rgba(250,250,250,.9);
    position: relative;
}
.header-fr label {
    line-height: 39px;
    font-size: 14px;
    color: #595959;
    padding: 0 4px 0 18px;
}
.wt-header .header-fr input {
    width: 50px;
    font-size: 16px;
    color: #000;
    font-weight: 400;
}
input, select, textarea {
    outline: 0;
    border: none;
    background: 0 0;
}
.xiazai {
    background: url("../assets/xiazai.png");
    margin-right: 0;
}
.download_drop_btn {
    position: relative;
    display: inline-block;
        margin-left: 60px;
    margin-top: 5px;
     cursor: pointer;
}
.xiazai {
    float: left;
    width: 24px;
    height: 24px;
    margin-right: 38px;
    margin-top: 3px;
    background-repeat: no-repeat;
    background-size: cover;
}
</style>