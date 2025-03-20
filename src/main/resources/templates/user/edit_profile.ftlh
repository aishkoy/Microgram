<#import "../layout.ftlh" as main>
<@main.layout>

    <h1>Edit Profile</h1>
    <form action="/user/profile/edit" method="post" class="w-50">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${profile.name}">
            </div>
            <div class="col-md-6 mb-3">
                <label for="surname" class="form-label">Surname</label>
                <input type="text" class="form-control" id="surname" name="surname" value="${profile.surname}">
            </div>
        </div>
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control" id="username" name="username" value="${profile.username}">
        </div>
        <div class="mb-3">
            <label for="gender" class="form-label">Gender</label>
            <select class="form-select" id="gender" name="gender">
                <option value="male">Male</option>
                <option value="female">Female</option>
                <option value="other">Other</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="aboutMe" class="form-label">About Me</label>
            <textarea class="form-control" id="aboutMe" name="aboutMe" rows="3"
                          <#if profile.aboutMe??>
                          value="${profile.aboutMe}"
            </#if>>
            </textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</@main.layout>