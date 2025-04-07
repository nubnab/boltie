import {AfterViewChecked, Component, computed, inject, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RequestsService} from '../../services/requests.service';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButton, MatMiniFabButton} from '@angular/material/button';
import {MatFormField, MatInput} from '@angular/material/input';
import {AuthService} from '../../services/auth.service';
import OvenPlayer from 'ovenplayer';
import {RxstompService} from '../../services/rxstomp.service';
import {MatIcon} from '@angular/material/icon';
import {MenuItem} from '../../layout/navigation/custom-sidenav/custom-sidenav.component';
import {map, Observable, startWith, Subscription} from 'rxjs';
import {
  MatAutocomplete,
  MatAutocompleteSelectedEvent,
  MatAutocompleteTrigger,
  MatOption
} from '@angular/material/autocomplete';
import {Category} from '../categories/categories.component';
import {AsyncPipe} from '@angular/common';

export type StreamTitle = {
  title: string;
}

export type StreamData = {
  id: number;
  username: string;
  title: string;
  categoryName: string;
  categoryUrl: string;
  streamUrl: string;
}

export type MessageDto = {
  chatRoomId: number;
  senderId: number;
  senderName: string;
  content: string;
  sentAt: string;
}

export type SimpleMessageDto = {
  content: string;
}

@Component({
  selector: 'app-stream',
  imports: [
    FormsModule,
    MatButton,
    MatInput,
    MatFormField,
    MatIcon,
    MatMiniFabButton,
    ReactiveFormsModule,
    MatAutocompleteTrigger,
    MatAutocomplete,
    MatOption,
    AsyncPipe,
  ],
  templateUrl: './stream.component.html',
  styleUrl: './stream.component.scss'
})

export class StreamComponent implements OnInit, AfterViewChecked, OnDestroy {
  id: number = 0;
  username: string = '';
  isEditMode: boolean = false;
  isCategoryEditMode: boolean = false;
  categoryControl = new FormControl('');
  filteredOptions: Observable<Category[]> = new Observable<Category[]>();

  streamTitle: string = '';
  streamLink: string = '';
  categoryName: string = '';
  categoryUrl: string = '';
  tempTitle: string = '';

  sendIcon: MenuItem = {
    icon: "chevron_right",
    label: "send_icon",
    route: ''
  }

  testMessage: SimpleMessageDto = { content: '' }
  messages: MessageDto[] = [];
  categories: Category[] = [];

  private requestsService = inject(RequestsService);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private rxStompService = inject(RxstompService);
  private router = inject(Router);

  private routeParamsSubscription!: Subscription;
  private stompMessageSubscription!: Subscription;
  private player: any;


  ngOnInit() {

    this.requestsService.getCategories().subscribe(categories => {
      this.categories = categories;
    })

    this.filteredOptions = this.categoryControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || '')),
    );

    this.routeParamsSubscription = this.route.params.subscribe(params => {
      this.username = params['username'];
    });

    this.requestsService.getStreamByUsername(this.username).subscribe({
      next: (data: StreamData) => {
        this.id = data.id;
        this.streamTitle = data.title;
        this.streamLink = data.streamUrl;
        this.categoryName = data.categoryName
        this.categoryUrl = data.categoryUrl

        this.initPlayer();

        this.requestsService.getRecentMessages(data.id).subscribe(messages => {
          messages.forEach(recentMessage => {
            this.messages.unshift(this.utcToLocal(recentMessage));
          })
        });

        this.stompMessageSubscription = this.rxStompService.watchMessages(data.id).subscribe((message) => {
          const receivedMessage: MessageDto = JSON.parse(message.body);
          this.messages.push(this.utcToLocal(receivedMessage));
        });
      },
      error: err => {console.log(err);}
    });
  }

  ngAfterViewChecked() {
    this.autoScroll();
  }

  ngOnDestroy() {
    if (this.routeParamsSubscription) {
      this.routeParamsSubscription.unsubscribe();
    }
    if (this.stompMessageSubscription) {
      this.stompMessageSubscription.unsubscribe();
    }
    if (this.player) {
      this.player.remove();
    }
    this.rxStompService.disconnect();
  }

  private _filter(value: string): Category[] {
    const filterValue = value.toLowerCase();

    return this.categories.filter(category =>
      category.name.toLowerCase().includes(filterValue)
    );
  }

  userIsStreamOwnerSignal = computed(() =>
    this.username === this.authService.currentUserSignal());

  editTitle() {
    this.tempTitle = this.streamTitle;
    this.isEditMode = true;
  }

  saveTitle() {
    const newTitle: StreamTitle = {
      title: this.streamTitle,
    }
    this.requestsService.editStreamTitle(newTitle).subscribe(res => {
      console.log(res); // return new title and set it
    })

    this.isEditMode = false;
  }

  cancelEdit() {
    this.streamTitle = this.tempTitle;
    this.isEditMode = false;
  }

  editCategory() {
    this.isCategoryEditMode = true;
  }

  cancelCategoryEdit() {
    this.isCategoryEditMode = false;
  }

  sendMessage() {
    if(this.testMessage.content.trim()){
      this.rxStompService.sendMessage('/app/chat/send/' + this.id, this.testMessage);
      this.testMessage = { content: '' };
    }
  }

  private utcToLocal(message: MessageDto): MessageDto {
    const utcTime = new Date(message.sentAt);
    message.sentAt = utcTime.toLocaleTimeString('en-GB', {
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
    });

    return message;
  }

  private autoScroll() {
    const chatContainer = document.getElementById('chatContainer');

    if(chatContainer) {
      chatContainer.scrollTop = chatContainer.scrollHeight;
    }
  }

  private initPlayer() {
    this.player = OvenPlayer.create('player_id', {
      sources: [{
        label: 'webrtc',
        type: 'webrtc',
        file: this.streamLink,
      }]
    });
  }

  navigateToCategory(categoryUrl: string) {
    this.router.navigate(['/categories/', categoryUrl]);
  }

  onCategorySelected(event: MatAutocompleteSelectedEvent): void {
    const selectedCategory: Category = event.option.value;

    this.categoryName = selectedCategory.name;
    this.categoryUrl = selectedCategory.url;
    this.isCategoryEditMode = false;

    this.requestsService.editStreamCategory(selectedCategory.id).subscribe(res => {
      console.log(res);
    })
  }

  displayCategory(category: Category): string {
    return category?.name || '';
  }

  //TODO: Stomp disconnect on destroy
  //TODO: forbid empty messages

}
