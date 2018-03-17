/*
 * Copyright 2018 Dmitriy Ponomarenko
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor
 * license agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership. The ASF licenses this
 * file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.dimowner.simpleweather.dagger.application

import android.content.Context
import com.dimowner.simpleweather.data.Prefs
import com.dimowner.simpleweather.data.remote.RestClient
import com.dimowner.simpleweather.data.repository.Repository
import com.dimowner.simpleweather.data.repository.RepositoryImpl
import com.dimowner.simpleweather.domain.main.WeatherContract
import com.dimowner.simpleweather.domain.main.WeatherPresenter
import com.dimowner.simpleweather.domain.metrics.MetricsContract
import com.dimowner.simpleweather.domain.metrics.MetricsPresenter
import com.dimowner.simpleweather.domain.welcome.WelcomePresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
		var appContext: Context
	) {

	@Provides
	@Singleton
	internal fun provideContext(): Context {
		return appContext
	}

	@Provides
	@Singleton
	internal fun providePrefs(context: Context): Prefs {
		return Prefs(context)
	}

	@Provides
	@Singleton
	internal fun provideRestClient(): RestClient {
		return RestClient()
	}

	@Provides
	@Singleton
	internal fun provideRepository(restClient: RestClient): Repository {
		return RepositoryImpl(restClient.weatherApi)
	}

	@Provides
	internal fun provideWelcomePresenter(prefs: Prefs, context: Context): WelcomePresenter {
		return WelcomePresenter(prefs, context)
	}

	@Provides
	internal fun provideMetricsPresenter(prefs: Prefs, context: Context): MetricsContract.UserActionsListener {
		return MetricsPresenter(prefs, context)
	}

	@Provides
	@Singleton
	internal fun provideWeatherPresenter(repository: Repository, prefs: Prefs, context: Context): WeatherContract.UserActionsListener {
		return WeatherPresenter(repository, prefs, context)
	}
}
