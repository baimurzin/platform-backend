<#import "base.ftl" as base>
<@base.base_layout>
    <div class="col s7 offset-s1">
        <div class="course-content">
            <h2 class="course-title">${step.title}</h2>
            <div class="content">
                <p class="grey-text text-darken-4">
                    ${step.content}
                </p>
            </div>
            <div class="btns-block row">
                <div class="col s6">
                    <a href="#" class="next-block-btn waves-effect waves-light center white-text teal lighten-1">ДАЛЬШЕ</a>
                </div>
                <div class="col s6">
                    <p class="block-left-inf valign-wrapper grey-text text-darken-1">TODO 4 блока осталось</p>
                </div>
            </div>
        </div>
    </div>
</@base.base_layout>