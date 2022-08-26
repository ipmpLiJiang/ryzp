<template>
  <a-upload
    disabled
    :file-list="fileList"
  >
  </a-upload>
</template>

<script>
export default {
  name: 'fileLook',
  data () {
    return {
      fileList: []
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
      default: 'staff'
    }
  },
  watch: {
    baseTime: function () {
      if (this.baseId !== '') {
        this.fileList = []
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
      this.findFileList(this.baseId)
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
        let flist = []
        for (let data of r.data.data) {
          data.url = this.$baseURL + data.url
          data.thumbUrl = this.$baseURL + data.thumbUrl
          flist.push(data)
        }
        this.fileList = flist
      })
    }
  }
}
</script>
<style scoped>
</style>
