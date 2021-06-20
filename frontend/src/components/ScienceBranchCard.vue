<template>
    <div class="science-branch-card" :class="{ selected }" @click="$emit('toggleSelect')">
        <!--<img class="icon" :src="icons" alt="Subject in promising direction">-->
    <div class="d-flex align-center">
        <img class="icon " :src="icons" alt="Subject in promising direction">
        <div class="name ml-4 mr-1">{{ name }}</div>
    </div>
        <div class="info-block">
            <div class="d-flex justify-space-between"><div>Кол-во технологий:&nbsp; </div><div><strong>{{ subjectsAmount }}</strong></div></div>             
            <div class="d-flex justify-space-between"><div class="super">Кол-во фронтиров:&nbsp; </div><div><strong >{{ frontiersAmount }}</strong></div></div>
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
        },
        icons: {
            type: String
        }
    }
};
</script>

<style scoped lang="scss">
.science-branch-card {
    border: 1px solid #d1d5db;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
    border-radius: 8px;
    padding: 25px;
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
    .icon {
        width: 60px;
        height:60px;
        /*background: #10b981;*/
        border-radius: 50%;
    }
</style>
