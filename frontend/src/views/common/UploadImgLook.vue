<template>
  <a-row type="flex" justify="center">
    <div v-if="isUpload">
      <img v-if="imageUrl" width="138px" height="178px" :src="imageUrl" alt="avatar" />
    </div>
  </a-row>
</template>

<script>
export default {
  name: 'file',
  data () {
    return {
      imageUrl: '',
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
    setTimeout(() => {
      if (this.baseId !== '') {
        this.findFileList(this.baseId)
      }
    }, 200)
  },
  methods: {
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
          this.isUpload = false
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
