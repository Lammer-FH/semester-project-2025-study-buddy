<template>
  <base-layout
    page-title="Edit Course"
    :show-back-button="true"
    default-back-link="/tabs/courses"
  >
    <course-edit-form :courseId="courseId" />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import BaseLayout from "@/components/template/BaseLayout.vue";
import CourseEditForm from "@/components/organisms/CourseEditForm.vue";

export default defineComponent({
  name: "CourseEditPage",
  components: {
    BaseLayout,
    CourseEditForm,
  },
  computed: {
    courseId(): number {
      return parseInt(this.$route.params.id);
    },
  },
});
</script>
