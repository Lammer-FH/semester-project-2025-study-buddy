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
      //   // Update in your store or array
      //   const index = this.tasks.findIndex((t) => t.id === updatedTask.id);
      //   if (index !== -1) {
      //     this.tasks.splice(index, 1, updatedTask);
      //   }
    },
    handleTaskDelete(taskId) {
      this.$emit("delete-task", taskId);
      //   this.tasks = this.tasks.filter((t) => t.id !== taskId);
    },
  },
};
</script>
