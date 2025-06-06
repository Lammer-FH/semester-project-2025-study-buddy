<template>
  <div class="ion-padding">
    <ion-list>
      <course-list-item
        v-for="item in courses"
        :key="item.id"
        :id="item.id"
        :title="item.title"
        @click="viewAssignments(item.id)"
        @delete="$emit('delete', $event)"
      >
      </course-list-item>
    </ion-list>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { IonList } from "@ionic/vue";
import CourseListItem from "@/components/molecules/CourseListItem.vue";

export default defineComponent({
  name: "CourseList",
  components: {
    IonList,
    CourseListItem,
  },
  props: ["courses"],
  emits: ["delete"],
  data() {
    return {};
  },
  methods: {
    viewAssignments(courseId: number) {
      // Use the exact path from your router configuration
      this.$router.push(`/tabs/course/${courseId}`);
    },
    // handleDelete(courseId: number) {
    //   this.$emit("delete", courseId);
    // },
  },
});
</script>
