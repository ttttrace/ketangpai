<template>
    <div class="wrap">
        <div class="homework-page">
            <div id="viewer-discuss" class="homework-cont gWidth">
                <div class="homework-list clearfix editor-page">
                    <div class="homework-box" style="padding: 0 20px;">
                        <div class="announce-cont-box">
                            <div class="title clearfix">
                                <h2>{{work.title}}</h2>
                            </div>
                            <div class="time" data-time="1570982340">
                                <div class="lefttime fl">
                                    截至：
                                    <span class="dates">{{work.deadline}}</span>
                                </div>
                            </div>
                            <div class="announce-cont">
                                <div class="word">
                                    <div class="p special" v-html="work.content">
                                    </div>
                                </div>
                                <div class="annex">
                                    <ul class="clearfix">
                                        <div
                                            class="file"
                                            v-for="file in work.file"
                                            :key="file"
                                            @mouseover="showDL"
                                            @mouseleave="closeDL"
                                           
                                        >
                                        <div   @click="view(file)">
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
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            work:{},
            DL:""
        }
    },
    mounted(){
        this.getWork(this.$route.params.workId);
    },
    methods: {
        view(file){
           window.open("http://127.0.0.1:8012/onlinePreview?url="+encodeURIComponent(file));
        },
        showDL(){
            this.DL=true;
        },
        closeDL(){
            this.DL=false;
        },

        getWork(id){
            this.$axios
                .get("api/homework/getWork?id=" + id)
                .then(res => {
                    if (res.data.file) {
                        res.data.file = res.data.file.split(",");
                    } else {
                        res.data.file = [];
                    }
                    this.work = res.data;
                })
                .catch(err => {
                    console.log(err);
                });
        },
        download(file) {
            window.open(file);
        },
    },
}
</script>

<style scoped>
body, dd, div, dl, dt, fieldset, form, h1, h2, h3, h4, h5, h6, img, input, li, ol, p, table, td, textarea, ul {
    margin: 0;
    padding: 0;
}
.dl span:hover{
    text-decoration: underline;
}
.dl{
    font-family:'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
    font-size:12px;
    margin-top:10px;
    height: 16px;
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
.homework-page {
    padding-top: 70px;
    padding-bottom: 60px;
}
.homework-cont {
    margin-top: 20px;
}
.gWidth {
    width: 810px;
}
.cWidth, .cWidth-new, .gWidth {
    margin-left: auto;
    margin-right: auto;
}
.clearfix {
    zoom: 1;
}
.homework-cont .homework-list .homework-box {
    padding: 0 20px 0 40px;
    border: 1px solid #E2E6ED;
    border-radius: 8px;
    background: #FFF;
    margin-bottom: 20px;
}
.announce-cont-box {
    padding-bottom: 10px;
    padding-right: 37px;
}
.announce-cont-box {
    padding: 0 20px 10px 30px;
    position: relative;
}
.title h2 {
    padding-top: 10px;
    float: left;
    height: 50px;
    line-height: 50px;
    font-size: 18px;
    font-weight: 400;
    width: 100%;
}
.announce-cont-box .title h2{
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.time {
    height: 34px;
    padding-top: 7px;
    line-height: 34px;
}
.announce-cont-box .time {
    font-size: 12px;
    color: #A0A0A0;
    margin-bottom: 6px;
}
.fl {
    float: left;
}
.word {
    width: 100%;
    height: auto;
    overflow: auto;
    max-height: inherit;
}
.announce-cont-box .announce-cont .word {
    line-height: 30px;
    overflow: hidden;
    font-size: 14px;
    word-wrap: break-word;
    word-break: break-all;
}
.announce-cont{
    padding: 0px 0px 40px 0px;
}
.p {
    color: #707070;
    overflow: hidden;
    line-height: 1.8;
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
    max-height: 300px;
    margin-top: 10px;
    overflow: hidden;
}
</style>