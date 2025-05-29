<template>
 <div class="ion-padding">
        <ion-list>
          <assignment-list-item v-for="item in assignments"
          :id="item.id"
          :title="item.title"
          :date="item.date"></assignment-list-item>
        </ion-list>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { IonList } from '@ionic/vue';
import AssignmentListItem from '@/components/AssignmentListItem.vue';

export default defineComponent({
  name: 'AssignmentList',
  components: {
    IonList,
    AssignmentListItem
  },
  props: 
    ["assignments"]
  ,
  data() {
    return {
      
    }
  }
})
</script>
