<template>
  <div
    :class="[
      multipage === true ? 'multi-page' : 'single-page',
      'not-menu-page',
      'home-page',
    ]"
  >
    <!-- <a-row :gutter="8" class="head-info"> 人才办 </a-row> -->
    <a-row :gutter="8" class="count-info">
      <staffApplyPoster-view
      v-if="this.$store.state.account.user.roleName === '注册用户'?true:false"
      >
      </staffApplyPoster-view>
      <recruitPoster-view
      :isend="0"
      v-if="this.$store.state.account.user.roleName === '注册用户'?false:true"
      >
      </recruitPoster-view>
    </a-row>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import moment from 'moment'
import StaffApplyPosterView from '@/views/common/StaffApplyPosterView'
import RecruitPosterView from '@/views/common/RecruitPosterView'
moment.locale('zh-cn')

export default {
  name: 'HomePage',
  components: {
    StaffApplyPosterView, RecruitPosterView
  },
  data () {
    return {
    }
  },
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    })
  },
  mounted () {
  },
  methods: {
  }
}
</script>
<style lang="less">
@import "../../static/less/Common";
</style>
