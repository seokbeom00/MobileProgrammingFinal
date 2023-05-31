package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ActivityChoiceBinding

class ChoiceActivity : AppCompatActivity() {
    lateinit var binding: ActivityChoiceBinding
    val Api_Ingredient:ArrayList<String> = ArrayList() //Api에서 재료 긁어오고 저장할 배열
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: choiceAdapter
    val model:ChoiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChoiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
    }

    fun init(){
        Api_Ingredient.add("당근")
        Api_Ingredient.add("오이")
        Api_Ingredient.add("돼지고기")
        Api_Ingredient.add("인육")
        Api_Ingredient.add("소 은밀한 분위")
        Api_Ingredient.add("이성민 이두박근")
        Api_Ingredient.add("홍석범 삼두박근")
        Api_Ingredient.add("커비")
        Api_Ingredient.add("바론 꼬리")
        Api_Ingredient.add("미니언 오른팔")
        Api_Ingredient.add("교수님")
        //임의의 값 일단 넣어놨습니다

        recyclerView = binding.recycle
        adapter = choiceAdapter(Api_Ingredient)
        adapter.itemClickListener = object:choiceAdapter.ItemClickListener{
            override fun OnItemClick(position: Int) {
                model.choice.value = Api_Ingredient[position]
                IngredientSelectFragment().show(
                    supportFragmentManager, "ingredient"
                )
            }
        }
        recyclerView.adapter = adapter
        binding.recycle.apply {
            layoutManager = LinearLayoutManager(context)
        }
    }
}