/*
 * Copyright (C) 2016 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.karumi.dexter;

import android.app.Activity;

import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import java.util.Collection;

public interface DexterBuilder {

  DexterBuilder onSameThread();

  DexterBuilder withErrorListener(PermissionRequestErrorListener errorListener);

  DexterBuilder withAlertDialog(String title, String message, Activity activity);
  DexterBuilder withFullScreenDialog(String title, String message, Activity activity);
  DexterBuilder withFullScreenDialogAndLayout(String title, String message, Activity activity, int resLayout);
  DexterBuilder withFullScreenDialogBackground(int resIdBackground, int textColor, int backgroundButton, int buttonTextColor);
  DexterBuilder withFullScreenDialogBackground(int resIdBackground);

  void check();

  interface Permission {
    DexterBuilder.SinglePermissionListener withPermission(String permission);

    DexterBuilder.MultiPermissionListener withPermissions(String... permissions);

    DexterBuilder.MultiPermissionListener withPermissions(Collection<String> permissions);
  }

  interface SinglePermissionListener {
    DexterBuilder withListener(PermissionListener listener);
  }

  interface MultiPermissionListener {
    DexterBuilder withListener(MultiplePermissionsListener listener);
  }

}