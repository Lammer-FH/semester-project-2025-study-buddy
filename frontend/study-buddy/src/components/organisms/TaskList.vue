<template>
  <div>
    <task-list-item
      v-for="task in tasks"
      :key="task.id"
      :task="task"
      @update-task="handleTaskUpdate"
      @delete-task="handleTaskDelete"
    />
  </div>
</template>

<script>
import TaskListItem from "@/components/molecules/TaskListItem.vue";
export default {
  name: "TaskList",
  components: { TaskListItem },
  props: ["tasks"],
  methods: {
    handleTaskUpdate(updatedTask) {
      this.$emit("update-task", updatedTask);
    },
    handleTaskDelete(taskId) {
      this.$emit("delete-task", taskId);
    },
  },
};
</script>
