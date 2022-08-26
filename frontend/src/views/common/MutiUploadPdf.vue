<template>
  <a-upload
    :accept="fileAccept"
    :disabled="!isEdit"
    :file-list="fileList"
    :multiple="false"
    :remove="handleImageRemove"
    :beforeUpload="beforeUpload"
  >
    <a-button size="small" v-if="isUpload"> <a-icon type="upload" /> 上传PDF </a-button>
  </a-upload>
</template>

<script>
export default {
  name: 'file',
  data () {
    return {
      fileList: [],
      fileAccept: '.pdf',
      uploading: false,
      isUpload: true
    }
  },
  props: {
    baseId: {
      default: ''
    },
    refTab: {
      default: 'staffInfo'
    },
    refType: {
      default: 'staffInfo'
    },
    size: {
      default: 1
    },
    isEdit: {
      default: true
    }
  },
  watch: {
    baseId: function () {
      if (this.baseId !== '' && !this.isEdit) {
        var _this = this
        setTimeout(() => {
          _this.findFileList(_this.baseId)
        }, 200)
      }
    },
    immediate: true, // watch侦听操作内的函数会立刻被执行
    deep: true
  },
  mounted () {
    setTimeout(() => {
      if (!this.isEdit) {
        this.isUpload = false
      }
      this.findFileList(this.baseId)
    }, 200)
  },
  methods: {
    beforeUpload (file) {
      const isPDF = file.type === 'application/pdf'
      if (!(isPDF)) {
        this.$error({
          title: '只能上传pdf 格式~'
        })
        return
      }
      const isLt3M = file.size / 1024 < 3001
      if (!isLt3M) {
        this.$error({
          title: '文件超3MB限制，不允许上传~'
        })
        return
      }
      return isPDF && isLt3M && this.customRequest(file)
    },
    customRequest (file) {
      const formData = new FormData()
      formData.append('file', file)
      formData.append('id', this.baseId)
      formData.append('refTab', this.refTab)
      formData.append('refType', this.refType)
      this.uploading = true
      let that = this
      this.$upload('comFile/uploadPdf', formData).then((r) => {
        if (r.data.data.success === 1) {
          that.fileList.push(r.data.data)
          this.uploading = false
          // this.$message.success('上传成功.')
          this.openNotificationIcon('success', '操作提醒', '上传文件成功.')
          this.lableErr = ''
          this.setBtnVisible()
        } else {
          // this.$message.error(r.data.data.message)
          this.openNotificationIcon('error', '操作提醒', r.data.data.message)
        }
      }).catch(() => {
        this.uploading = false
        // this.$message.error('上传失败.')
        this.openNotificationIcon('error', '操作提醒', '上传文件失败.')
      })
    },
    openNotificationIcon (type, title, description) {
      // success, info, warning, error
      this.$notification[type]({
        duration: 3,
        message: title,
        description: description
      })
    },
    handleImageRemove (file) {
      let that = this
      this.$confirm({
        title: '确定删除所选中的文件?',
        content: '当您点击确定按钮后，这些文件将会被彻底删除',
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
              that.openNotificationIcon('success', '操作提醒', '删除文件成功.')
              const index = that.fileList.indexOf(file)
              const newFileList = that.fileList.slice()
              newFileList.splice(index, 1)
              that.fileList = newFileList
              that.setBtnVisible()
            } else {
              // that.$message.error(r.data.data.message)
              that.openNotificationIcon('error', '操作提醒', r.data.data.message)
            }
          }).catch(() => {
            // that.$message.error('删除失败.')
            that.openNotificationIcon('error', '操作提醒', '删除文件失败.')
          })
        },
        onCancel () {
        }
      })
    },
    handleCancel () {
      this.previewVisible = false
    },
    setBtnVisible () {
      if (this.fileList.length === this.size) {
        this.isUpload = false
      } else {
        this.isUpload = true
      }
    },
    findFileList (id) {
      let formData = {}
      formData.id = id
      formData.refType = this.refType
      formData.refTab = this.refTab
      this.fileList = []
      this.$post('comFile/listImgComFile', {
        ...formData
      }).then((r) => {
        for (let data of r.data.data) {
          data.url = this.$baseURL + data.url
          data.thumbUrl = this.$baseURL + data.thumbUrl
          this.fileList.push(data)
          this.setBtnVisible()
        }
      })
    }
  }
}
</script>
<style scoped>
</style>
