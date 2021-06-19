<template>
    <div class="science-branch-card" :class="{ selected }" @click="$emit('toggleSelect')">
        <div class="name">{{ name }}</div>
        <div class="info-block">
            <div>Кол-во тематик: {{ subjectsAmount }}</div>
            <div>Кол-во фронтиров: {{ frontiersAmount }}</div>
        </div>
    </div>
</template>

<script>
export default {
    name: "ScienceBranchCard",
    props: {
        name: {
            type: String,
            required: true
        },
        subjectsAmount: {
            type: Number,
            required: true
        },
        frontiersAmount: {
            type: Number,
            required: true
        },
        selected: {
            type: Boolean,
            required: true
        }
    }
};
</script>

<style scoped lang="scss">
.science-branch-card {
    border: 1px solid #d1d5db;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
    border-radius: 8px;
    padding: 30px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    cursor: pointer;
	user-select: none;

    &.selected {
        background-color: #d1fae5;
        border-color: #10b981;
        color: #047857;
    }
}
</style>
