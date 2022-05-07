package com.example.storyapp

import com.example.storyapp.database.RemoteKeys

object DataDummy {
    fun generateDummyRemoteKeys(): List<RemoteKeys> {
        val newsList = ArrayList<RemoteKeys>()
        for (i in 1..5) {
            val keys = RemoteKeys(
                "id: $i",
                i,
                i + 2

            )
            newsList.add(keys)
        }
        return newsList
    }

    fun generateDummyNewsEntity(): List<ListStory> {
        val newsList = ArrayList<ListStory>()
        for (i in 0..5) {
            val stories = ListStory(
                "Title $i",
                "this is name",
                "This is description",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                null,
                null,
            )
            newsList.add(stories)
        }
        return newsList
    }

    fun generateDummyNewStories(): List<ListStoryPaging> {
        val newsList = ArrayList<ListStoryPaging>()
        for (i in 0..5) {
            val stories = ListStoryPaging(
                "Title $i",
                "this is name",
                "This is description",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                null,
                null,
            )
            newsList.add(stories)
        }
        return newsList
    }


    fun generateDummyRequestLogin(): RequestLogin {
        return RequestLogin("123@gmail.com", "123456")
    }

    fun generateDummyResponseLogin(): ResponseLogin {
        val newLoginREsult = LoginResult("qwerty", "monica", "ini-token")
        return ResponseLogin(false, "Login successfully", newLoginREsult)
    }

    fun generateDummyRequestRegister(): RequestRegister {
        return RequestRegister("monica", "123@gmail.com", "123456")

    }
}