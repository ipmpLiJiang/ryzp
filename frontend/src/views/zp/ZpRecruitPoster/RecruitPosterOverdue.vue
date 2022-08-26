<template>
  <a-card :bordered="false" class="card-area">
    <recruitPoster-view :isend="1"> </recruitPoster-view>
  </a-card>
</template>

<script>
import moment from 'moment'
import RecruitPosterView from '../../common/RecruitPosterView'
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'zpRecruitPosterOverdue',
  components: { RecruitPosterView },
  data () {
    return {
      formItemLayout
    }
  },
  computed: {
  },
  mounted () {
  },
  methods: {
    moment
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
