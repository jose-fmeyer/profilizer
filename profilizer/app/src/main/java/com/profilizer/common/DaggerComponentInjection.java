//package com.profilizer.common;
//
//import android.app.Application;
//
//import com.profilizer.common.module.NetworkModule;
//import com.profilizer.personalitytest.di.components.LatestTestsComponent;
//import com.profilizer.personalitytest.di.components.ListCompletedTestComponent;
//import com.profilizer.personalitytest.di.components.PersonalityTestComponent;
//import com.profilizer.personalitytest.di.components.QuestionComponent;
//import com.profilizer.personalitytest.di.components.StartPersonalityTestComponent;
//import com.profilizer.personalitytest.di.components.StartTestComponent;
//
//public class DaggerComponentInjection {
//
//
//    companion object
//
//    {
//        lateinit var networkModule: NetworkModule
//        var latestTestsComponent:LatestTestsComponent ? = null
//        var listCompletedTestComponent:ListCompletedTestComponent ? = null
//        var personalityTestComponent:PersonalityTestComponent ? = null
//        var questionComponent:QuestionComponent ? = null
//        var startTestComponent:StartTestComponent ? = null
//        var startPersonalityTestComponent:StartPersonalityTestComponent ? = null
//
//        fun init (application:Application, url:String){
//        networkModule = NetworkModule(application, url)
//    }
//
//        fun inject (view:GalleryListActivity){
//        initGalleryModule()
//        initGalleryListModule(view)
//        DaggerGalleryListComponent.builder()
//                .galleryListModule(galleryListModule)
//                .galleryModule(galleryModule)
//                .networkModule(networkModule)
//                .build()
//                .inject(view)
//    }
//
//    private fun initGalleryListModule(view:GalleryListActivity) {
//        if (galleryListModule == null) {
//            galleryListModule = GalleryListModule(view)
//        } else {
//            galleryListModule ?.view = view
//        }
//    }
//
//    private fun initGalleryModule() {
//        if (galleryModule == null) {
//            galleryModule = GalleryModule()
//        }
//    }
//
//    fun inject(view:GalleryUploaderFragment) {
//        initGalleryModule()
//        initGalleryUploaderModule(view)
//        DaggerGalleryUploaderComponent.builder()
//                .galleryModule(galleryModule)
//                .galleryUploaderModule(galleryUploaderModule)
//                .networkModule(networkModule)
//                .build()
//                .inject(view)
//    }
//
//    private fun initGalleryUploaderModule(view:GalleryUploaderFragment) {
//        if (galleryUploaderModule == null) {
//            galleryUploaderModule = GalleryUploaderModule(view)
//        } else {
//            galleryUploaderModule ?.view = view
//        }
//    }
//
//    fun inject(activity:GalleryConfirmationActivity) {
//        initGalleryModule()
//        if (galleryConfirmationModule == null) {
//            galleryConfirmationModule = GalleryConfirmationModule(activity)
//        } else {
//            galleryConfirmationModule ?.view = activity
//        }
//
//        DaggerGalleryConfirmationComponent.builder()
//                .galleryConfirmationModule(galleryConfirmationModule)
//                .networkModule(networkModule)
//                .galleryModule(galleryModule)
//                .build()
//                .inject(activity)
//    }
//}
//}
