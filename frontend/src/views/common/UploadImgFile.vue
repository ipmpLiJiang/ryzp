<template>
  <a-row type="flex" justify="center">
    <a-upload
      name="avatar"
      :accept="fileAccept"
      list-type="picture-card"
      class="avatar-uploader"
      :disabled="!isEdit"
      :show-upload-list="false"
      :before-upload="beforeUpload"
    >
      <div v-if="isUpload">
      <img v-if="imageUrl" width="128px" height="168px" :src="imageUrl" alt="avatar" />
      <div v-else>
        <a-icon :type="uploading ? 'loading' : 'plus'" />
        <div class="ant-upload-text">上传图片</div>
      </div>
      </div>
    </a-upload>
  </a-row>
</template>

<script>
export default {
  name: 'file',
  data () {
    return {
      imageUrl: '',
      fileAccept: '.jpg,.png',
      uploading: false,
      isUpload: true
    }
  },
  props: {
    baseId: {
      default: ''
    },
    baseTime: {
      default: 0
    },
    refTab: {
      default: 'staffInfo'
    },
    refType: {
      default: 'photo'
    },
    isEdit: {
      default: true
    }
  },
  watch: {
    baseTime: function () {
      if (this.baseId !== '') {
        this.imageUrl = ''
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
  },
  methods: {
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
      formData.append('id', this.baseId)
      formData.append('refTab', this.refTab)
      formData.append('refType', this.refType)
      this.uploading = true
      let that = this
      this.$upload('comFile/uploadImg', formData).then((r) => {
        if (r.data.data.success === 1) {
          that.imageUrl = r.data.data.url
          this.uploading = false
          // this.$message.success('上传成功.')
          this.openNotificationIcon('success', '操作提醒', '上传图片成功.')
        } else {
          // this.$message.error(r.data.data.message)
          this.openNotificationIcon('error', '操作提醒', r.data.data.message)
        }
      }).catch(() => {
        this.uploading = false
        // this.$message.error('上传失败.')
        this.openNotificationIcon('error', '操作提醒', '上传图片失败.')
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
    findFileList (id) {
      let formData = {}
      formData.id = id
      formData.refType = this.refType
      this.$post('comFile/listImgComFile', {
        ...formData
      }).then((r) => {
        if (r.data.data.length > 0) {
          this.isUpload = true
          this.imageUrl = this.$baseURL + r.data.data[0].url
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
.avatar-uploader > .ant-upload {
  width: 128px;
  height: 168px;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
