import { Pipe, PipeTransform } from '@angular/core';
import { ValidationErrors } from '@angular/forms';

@Pipe({ name: 'vfmValidationErrorI18N' })
export class ValidationErrorI18nPipe implements PipeTransform {
  private errors: any = {
    required: () => $localize`Required field!`,
    email:()=> $localize`Wrong email format!`,
    incorrectRepeatPassword:()=> $localize`The passwords do not match!`
  };

  constructor() {}

  public transform(errors: ValidationErrors | null | undefined): string {
    let message = '';
    if (errors) {
      Object.entries(errors || {}).map((e) => {
        if (message) {
          return;
        }

        if (e[1]) {
          message = this.errors[e[0]](e[1]);
        }
      });
    }
    return message;
  }
}
