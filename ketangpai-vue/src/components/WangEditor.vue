
<template lang="html">
  <div class="editor">
    <div ref="toolbar" class="toolbar">
    </div>
    <div ref="editor" class="text">
    </div>
  </div>
</template>

<script>
import E from "wangeditor";
export default {
    name: "editoritem",
    data() {
        return {
            // uploadPath,
            editor: null,
            info_: null,
            imgsrc: [],
        };
    },
    model: {
        prop: "value",
        event: "change"
    },
    props: {
        value: {
            type: String,
            default: ""
        },
        isClear: {
            type: Boolean,
            default: false
        }
    },
    watch: {
        isClear(val) {
            // 触发清除文本域内容
            if (val) {
                this.editor.txt.clear();
                this.info_ = null;
            }
        },
        value: function(value) {
            if (value !== this.editor.txt.html()) {
                this.editor.txt.html(this.value);
            }
        }
    },
    mounted() {
        this.seteditor();
        this.editor.txt.html(this.value);
    },
    methods: {
        seteditor() {
            this.editor = new E(this.$refs.toolbar, this.$refs.editor);
            this.editor.customConfig.uploadImgShowBase64 = false; // base 64 存储图片
            this.editor.customConfig.uploadImgServer = "api/upload/upload"; // 配置服务器端地址
            this.editor.customConfig.uploadImgHeaders = {}; // 自定义 header
            this.editor.customConfig.uploadFileName = "file"; // 后端接受上传文件的参数名
            this.editor.customConfig.uploadImgMaxSize = 2 * 1024 * 1024; // 将图片大小限制为 2M
            this.editor.customConfig.uploadImgMaxLength = 6; // 限制一次最多上传 6 张图片
            this.editor.customConfig.uploadImgTimeout = 3 * 60 * 1000; // 设置超时时间
            this.editor.customConfig.debug = true;

            // 配置菜单
            this.editor.customConfig.menus = [
                "head", // 标题
                "bold", // 粗体
                "fontSize", // 字号
                "fontName", // 字体
                "italic", // 斜体
                "underline", // 下划线
                "strikeThrough", // 删除线
                "foreColor", // 文字颜色
                "backColor", // 背景颜色
                "link", // 插入链接
                "list", // 列表
                "justify", // 对齐方式
                "image", // 插入图片
                "video", // 插入视频
                "code", // 插入代码
                "undo", // 撤销
                "redo" // 重复
            ];

            this.editor.customConfig.uploadImgHooks = {
                fail: (xhr, editor, result) => {
                    // 插入图片失败回调
                    console.log("插入图片失败回调");
                },
                success: (xhr, editor, result) => {
                    // 图片上传成功回调
                    console.log("图片上传成功回调");
                },
                timeout: (xhr, editor) => {
                    // 网络超时的回调
                    console.log("网络超时的回调");
                },
                error: (xhr, editor) => {
                    // 图片上传错误的回调
                    console.log("图片上传错误的回调");
                },
                customInsert: (insertImg, result, editor) => {
                    let url = result.url[0];
                    this.imgsrc.push(url);
                    insertImg(url);
                    this.$emit("url", this.imgsrc);
                }
            };
            this.editor.customConfig.uploadImg;
            this.editor.customConfig.onchange = html => {
                this.change(html);
                this.info_ = html;
                this.$emit("change", this.info_); 
            };
            this.editor.create();
        },
        change(html) {
            if (this.imgsrc.length !== 0) {
                let nowimgs = this.getSrc(html);
                let merge = this.imgsrc
                    .concat(nowimgs)
                    .filter(function(v, i, arr) {
                        return arr.indexOf(v) === arr.lastIndexOf(v);
                    });
                for (let x in merge) {
                    let colds = merge[x];
                    console.log(colds);
                    this.deleteImage(colds); 
                }
                this.imgsrc = nowimgs;
                this.$emit("url", this.imgsrc);
            }
        },

        getSrc(html) {
            var imgReg = /<img.*?(?:>|\/>)/gi;
            var srcReg = /src=[\\"]?([^\\"]*)[\\"]?/i;
            var arr = html.match(imgReg);
            let imgs = [];
            if (arr) {
                for (let i = 0; i < arr.length; i++) {
                    var src = arr[i].match(srcReg)[1];
                    imgs.push(src);
                }
            }
            return imgs;
        },
        deleteImage(colds) {
            this.$axios
                .post("api/upload/removeFile?path=" + colds)
                .then(res => {})
                .catch(err => {
                    alert(err.response.data.msg);
                    console.log(err);
                });
        }
    }
};
</script>

<style lang="css">
.editor {
    width: 100%;
    margin: 0 auto;
    position: relative;
    z-index: 0;
}
.toolbar {
    border-bottom: 1px solid #ccc;
}
.text {
    min-height: 150px;
    border-bottom: 1px solid #ccc;
}
.w-e-text {
    height: 150px !important;
}

</style>