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
        @edit="$emit('edit', $event)"
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
  emits: ["delete", "edit", "view"],
  data() {
    return {};
  },
  methods: {
    viewAssignments(courseId: number) {
      this.$emit("view", courseId);
    },
  },
});
</script>
