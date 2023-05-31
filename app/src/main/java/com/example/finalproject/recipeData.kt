package com.example.finalproject

import java.io.Serializable

//recipe 정보에 대한 Data 정보
data class recipeData(var recipe_CompletePicture_url: String,var recipeProcess_pictures_url: List<String>,
                 var recipe_name: String, var recipe_process: List<String>, var recipe_stuff: List<String>):Serializable