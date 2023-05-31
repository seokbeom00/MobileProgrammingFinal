package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentRecipeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeFragment : Fragment() {

    //임시 테스트용 Data
    var havingIngredient_List: ArrayList<Ingredient> = ArrayList()
    val intent = Intent()

    //일시적인 테스트를 위한 임시 recipeDataList 생성
    var recipeData_List: ArrayList<recipeData> = arrayListOf(
        recipeData("https://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_2.png", arrayListOf("https://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png", "https://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png"),
            "테스트 레시피1", arrayListOf("1번: 볶는다", "2번: 완성!"), arrayListOf("당근 200g", "바나나 50g", "불닭소스 200g")
        ),
        recipeData("https://www.foodsafetykorea.go.kr/uploadimg/cook/10_00029_2.png", arrayListOf("https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054834_1675673314720.jpg", "https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054908_1675673348152.jpg"),
            "테스트 레시피2", arrayListOf("1번: 쪄버린다", "2번: 다져준다", "3번: 완성!"), arrayListOf("오이 50g", "날콩가루 7g", "다진 대파 5g", "참기름 2g")
        ),
        recipeData("https://www.foodsafetykorea.go.kr/uploadimg/cook/10_00029_2.png", arrayListOf("https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054834_1675673314720.jpg", "https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054908_1675673348152.jpg"),
            "곰국", arrayListOf("1번: 쪄버린다", "2번: 다져준다", "3번: 완성!"), arrayListOf("오이 50g", "날콩가루 7g", "다진 대파 5g", "참기름 2g")
        ),
        recipeData("https://www.foodsafetykorea.go.kr/uploadimg/cook/10_00029_2.png", arrayListOf("https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054834_1675673314720.jpg", "https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054908_1675673348152.jpg"),
            "테스트 레시피 4입니다", arrayListOf("1번: 쪄버린다", "2번: 다져준다", "3번: 완성!"), arrayListOf("오이 50g", "날콩가루 7g", "다진 대파 5g", "참기름 2g", "공기밥 200g")
        ),
        recipeData("https://www.foodsafetykorea.go.kr/uploadimg/cook/10_00029_2.png", arrayListOf("https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054834_1675673314720.jpg", "https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054908_1675673348152.jpg"),
            "북어국", arrayListOf("1번: 쪄버린다", "2번: 다져준다", "3번: 한번 더 구워준다", "4번: 완성!"), arrayListOf("오이 50g", "날콩가루 7g", "다진 대파 5g", "참기름 2g", "강낭콩 30g")
        ),
        recipeData("https://www.foodsafetykorea.go.kr/uploadimg/cook/10_00029_2.png", arrayListOf("https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054834_1675673314720.jpg", "https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054908_1675673348152.jpg"),
            "부대찌개", arrayListOf("1번: 쪄버린다", "2번: 다져준다", "3번: 완성!"), arrayListOf("오이 50g", "날콩가루 7g", "다진 대파 5g", "참기름 2g")
        ),
        recipeData("https://www.foodsafetykorea.go.kr/uploadimg/cook/10_00029_2.png", arrayListOf("https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054834_1675673314720.jpg", "https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054908_1675673348152.jpg"),
            "당근 프라페", arrayListOf("1번: 쪄버린다", "2번: 다져준다", "3번: 완성!"), arrayListOf("오이 50g", "날콩가루 7g", "다진 대파 5g", "참기름 2g")
        ),
        recipeData("https://www.foodsafetykorea.go.kr/uploadimg/cook/10_00029_2.png", arrayListOf("https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054834_1675673314720.jpg", "https://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054908_1675673348152.jpg"),
            "집가고싶다", arrayListOf("1번: 쪄버린다", "2번: 다져준다", "3번: 완성!", "4번: 아직 안끝났다", "5번: 더 굽는다", "6번: 드디어 완성!", "7번: 아직 안 끝났다..", "8번: 구라고 드디어 완성!"), arrayListOf("오이 50g", "날콩가루 7g", "다진 대파 5g", "참기름 2g")
        )
    )

    //ViewBinding 기법을 사용하기 위해 binding 객체 하나 생성
    lateinit var binding: FragmentRecipeBinding

    //안드로이드에서는 https로 시작하는 주소만 허용한다 (http는 허용되지 않음)
    val recipe_url = "https://openapi.foodsafetykorea.go.kr/api/081a891c3e974bed9221/COOKRCP01/xml/1/15"

    //Coroutine 객체를 사용할 것임
    val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //어댑터 생성하고 세팅하는 함수 호출
        setupRecyclerViewInRecipeFragment()

    }

    //어댑터 생성하고 세팅하는 함수
    private fun setupRecyclerViewInRecipeFragment()
    {
        //recyclerView 어댑터와 layoutManager를 생성하고 세팅한다
        //requireContext() 함수를 통해 현재 Fragment의 context 정보를 가져온다
        binding.wholeRecipeRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        val recipe_Adapter = recipeAdapter(recipeData_List, havingIngredient_List)

        //테스트용
        havingIngredient_List.add(Ingredient("당근", 3, 2023, 4, 14, 2023, 5, 30))
        havingIngredient_List.add(Ingredient("당근", 3, 2023, 4, 14, 2023, 5, 30))

        recipe_Adapter.itemClickListener = object: recipeAdapter.OnItemClickListener {
            override fun OnItemClick(item: recipeData, position: Int) {
                //레시피 전체 정보를 띄우는 Activity로 넘어가도록 한다
                navigateToAnotherActivity(item, position)
            }
        }

        //RecyclerView에게 우리가 정의한 adapter를 이용하여 화면에 띄우라고 알려주는 구문
        binding.wholeRecipeRecyclerView.adapter = recipe_Adapter

    }

    //레시피 전체 정보를 띄우는 Activity로 넘어가도록 하는 함수
    private fun navigateToAnotherActivity(item: recipeData, position: Int) {
        val intent = Intent(requireContext(), recipeDetailActivity::class.java)
        val message = recipeData_List[position]

        intent.putExtra("recipeData", message)
        startActivity(intent)
    }

    fun getRecipeData()
    {

    }

}