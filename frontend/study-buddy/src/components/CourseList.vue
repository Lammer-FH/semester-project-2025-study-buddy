<template>
 <div class="ion-padding">
        <ion-list>
          <course-list-item v-for="item in courses"
          :id="item.id"
          :title="item.title"
          :router-link="`course/${item.id}`">
        </course-list-item>
        </ion-list>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { IonList } from '@ionic/vue';
import CourseListItem from '@/components/CourseListItem.vue';

export default defineComponent({
  name: 'CourseList',
  components: {
    IonList,
    CourseListItem
  },
  props: 
    ["courses"]
  ,
  data() {
    return {
      
    }
  }
})
</script>
