<template>
  <!--上传图片-->
  <a-row type="flex" justify="center">
    <a-col>
      <div class="clearfix">
        <a-upload
          list-type="picture-card"
          accept=".jpg,.png"
          v-if="isUpload"
          :disabled="!isEdit"
          :file-list="fileList"
          :remove="handleImageRemove"
          :beforeUpload="beforeUpload"
          @preview="handlePreview"
          class="upload-list-inline"
        >
          <div v-if="fileList.length < this.size">
            <a-icon type="plus" />
            <div class="ant-upload-text">上传图片</div>
          </div>
        </a-upload>
        <a-modal
          width="85%"
          :visible="previewVisible"
          :footer="null"
          @cancel="handleCancel"
        >
          <div style="text-align: center">
            <img
              alt="example"
              style="
                width: auto;
                height: auto;
                max-width: 100%;
                max-height: 100%;
              "
              :src="previewImage"
            />
          </div>
        </a-modal>
      </div>
    </a-col>
  </a-row>
</template>

<script>
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'file',
  data () {
    return {
      fileList: [],
      uploading: false,
      previewVisible: false,
      previewImage: '',
      isUpload: true
    }
  },
  props: {
    baseParms: {
      default: {
        id: '',
        t: 0
      }
    },
    refTab: {
      default: 'staffInfo'
    },
    size: {
      default: 1
    },
    refType: {
      default: 'photo'
    },
    isEdit: {
      default: true
    }
  },
  watch: {
    baseParms: function () {
      if (this.baseParms.id !== '' && !this.isEdit) {
        var _this = this
        setTimeout(() => {
          _this.findFileList(_this.baseParms.id)
        }, 200)
      }
    },
    immediate: true, // watch侦听操作内的函数会立刻被执行
    deep: true
  },
  mounted () {
    setTimeout(() => {
      if (this.baseParms.id !== '') {
        this.findFileList(this.baseParms.id)
      }
    }, 200)
  },
  methods: {
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    beforeUpload (file) {
      // 限制图片 格式、size、分辨率
      const isJPG = file.type === 'image/jpg'
      const isJPEG = file.type === 'image/jpeg'
      const isPNG = file.type === 'image/png'
      // const isGIF = file.type === 'image/gif'
      // const isPNG = file.type === 'image/png'
      if (!(isJPG || isJPEG || isPNG)) {
        this.$error({
          title: '只能上传JPG或PNG 格式的图片~'
        })
        return
      }
      const isLt2M = file.size / 1024 < 301
      if (!isLt2M) {
        this.$error({
          title: '超300KB限制，不允许上传~'
        })
        return
      }
      return (isJPG || isJPEG || isPNG) && isLt2M && this.customRequest(file)
    },
    customRequest (file) {
      const formData = new FormData()
      formData.append('file', file)
      formData.append('id', this.baseParms.id)
      formData.append('refTab', this.refTab)
      formData.append('refType', this.refType)
      this.uploading = true
      let that = this
      this.$upload('comFile/uploadImg', formData).then((r) => {
        if (r.data.data.success === 1) {
          that.fileList.push(r.data.data)
          this.uploading = false
          // this.$message.success('上传成功.')
          this.openNotificationIcon('success', '操作提醒', '上传成功.')
          this.lableErr = ''
        } else {
          // this.$message.error(r.data.data.message)
          this.openNotificationIcon('error', '操作提醒', r.data.data.message)
        }
      }).catch(() => {
        this.uploading = false
        // this.$message.error('上传失败.')
        this.openNotificationIcon('error', '操作提醒', '上传失败.')
      })
    },
    handleImageRemove (file) {
      let that = this
      this.$confirm({
        title: '确定删除所选中的图片?',
        content: '当您点击确定按钮后，这些图片将会被彻底删除',
        centered: true,
        onOk () {
          let formData = {
            id: file.uid,
            refType: that.refType,
            refTab: that.refTab
          }
          that.$post('comFile/deleteImg', formData).then((r) => {
            that.uploading = false
            if (r.data.data.success === 1) {
              // that.$message.success('删除成功')
              that.openNotificationIcon('success', '操作提醒', '删除成功.')
              const index = that.fileList.indexOf(file)
              const newFileList = that.fileList.slice()
              newFileList.splice(index, 1)
              that.fileList = newFileList
            } else {
              // that.$message.error(r.data.data.message)
              that.openNotificationIcon('error', '操作提醒', r.data.data.message)
            }
          }).catch(() => {
            // that.$message.error('删除失败.')
            that.openNotificationIcon('error', '操作提醒', '删除失败.')
          })
        },
        onCancel () {
        }
      })
    },
    handleCancel () {
      this.previewVisible = false
    },
    openNotificationIcon (type, title, description) {
      // success, info, warning, error
      this.$notification[type]({
        duration: 3,
        message: title,
        description: description
      })
    },
    findFileList (id) {
      let formData = {}
      formData.id = id
      formData.refType = this.refType
      this.fileList = []
      this.$post('comFile/listImgComFile', {
        ...formData
      }).then((r) => {
        if (r.data.data.length > 0) {
          this.isUpload = true
          for (let data of r.data.data) {
            data.url = this.$baseURL + data.url
            data.thumbUrl = this.$baseURL + data.thumbUrl
            this.fileList.push(data)
          }
        } else {
          if (!this.isEdit) {
            this.isUpload = false
          } else {
            this.isUpload = true
          }
        }
      })
    }
  }
}
</script>
<style scoped>
/* you can make up upload button and sample style by using stylesheets */
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}
.ant-row-flex-center {
  padding: 10px;
  justify-content: left;
}
.upload-list-inline >>> .ant-upload-list-item {
  /* width: 155px;
  height: 190px; */
}
/* .clearfix {
  width: 180px;
  height: 200px;
} */
</style>
