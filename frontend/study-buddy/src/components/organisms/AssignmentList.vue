<template>
  <div class="ion-padding">
    <ion-list>
      <assignment-list-item
        v-for="item in assignments"
        :key="item.id"
        :id="item.id"
        :title="item.title"
        :deadline="item.deadline"
      ></assignment-list-item>
    </ion-list>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { IonList } from "@ionic/vue";
import AssignmentListItem from "@/components/molecules/AssignmentListItem.vue";

export default defineComponent({
  name: "AssignmentList",
  components: {
    IonList,
    AssignmentListItem,
  },
  props: ["assignments"],
  data() {
    return {};
  },
});
</script>
